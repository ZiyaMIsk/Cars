package com.turkcell.RentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.RentACar.business.abstracts.CustomerService;
import com.turkcell.RentACar.business.abstracts.InvoiceService;
import com.turkcell.RentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.customer.CustomerDto;
import com.turkcell.RentACar.business.dtos.invoice.InvoiceDto;
import com.turkcell.RentACar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByRentingIdDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByIdDto;
import com.turkcell.RentACar.business.requests.create.CreateInvoiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorDataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.RentACar.entities.Customer;
import com.turkcell.RentACar.entities.Invoice;
import com.turkcell.RentACar.entities.Renting;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	private RentingService rentingService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private AdditionalServiceService additionalServiceService;
	

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, CustomerService customerService,RentingService rentingService,
			OrderedAdditionalServiceService orderedAdditionalServiceService, AdditionalServiceService additionalServiceService) {
		
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.rentingService = rentingService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.additionalServiceService = additionalServiceService;
	
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
	
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		
		CustomerDto customer = this.customerService.getByCustomerId(createInvoiceRequest.getCustomerId()).getData();
		
		Customer c = this.modelMapperService.forDto().map(customer, Customer.class);
		
		invoice.setCustomer(c);	
		
		checkIfInvoiceNoExists(invoice.getInvoiceNo());
		
		idCorrectionForAdd(invoice, createInvoiceRequest);

		invoiceTableSetColumns(invoice, createInvoiceRequest);
		
		checkIfCustomerExists(invoice.getCustomer().getUserId());
		
		checkIfRentingExists(invoice.getRenting().getRentingId());
		
		checkInvoiceIfRentingExists(invoice.getRenting().getRentingId());

		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Added");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfInvoiceExists(id);
		this.invoiceDao.deleteById(id);
		
		return new SuccessResult(Messages.INVOICEDELETED);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		var result = this.invoiceDao.findAll();
		
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		response = idCorrectionGetAll(result, response);
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, Messages.INVOICELISTED);
	}

	@Override
	public Result update(int id, UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceExists(id);
		
		Invoice invoice = this.invoiceDao.getByInvoiceId(id);
		
		updateOperation(invoice,updateInvoiceRequest);
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult(Messages.INVOICEUPDATED);
	}


	@Override
	public DataResult<InvoiceDto> getByInvoiceId(int invoiceId) throws BusinessException {
		
		Invoice result = this.invoiceDao.getByInvoiceId(invoiceId);
		
		InvoiceDto response = this.modelMapperService.forDto().map(result,InvoiceDto.class);
		idCorrectionForGetById(result, response);
		
		return new ErrorDataResult<InvoiceDto>(Messages.INVOICENOTFOUND);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {
		
		List<Invoice> result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, Messages.INVOICESLISTEDBYDATES);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {
		
		List<Invoice> result = this.invoiceDao.getByCustomer_UserId(id);
		
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, Messages.INVOICEFOUNDBYCUSTOMERID);
	}

	private void updateOperation(Invoice invoice, UpdateInvoiceRequest updateInvoiceRequest) {
		invoice.setCreateDate(updateInvoiceRequest.getCreateDate());;
		
	}

	private void idCorrectionForAdd(Invoice invoice, CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

		CustomerDto getCustomerByIdDto = this.customerService.getByCustomerId(invoice.getCustomer().getUserId()).getData();
		Customer customer = this.modelMapperService.forDto().map(getCustomerByIdDto, Customer.class);

		RentingByIdDto rentingByIdDto = this.rentingService.getRentingById(invoice.getRenting().getRentingId()).getData();
		Renting renting = this.modelMapperService.forDto().map(rentingByIdDto, Renting.class);

		invoice.setCustomer(customer);
		invoice.setRenting(renting);
	}
	private void idCorrectionForGetById(Invoice invoice, InvoiceDto invoiceDto) {

		invoiceDto.setCustomerId(invoice.getCustomer().getUserId());
		invoiceDto.setRentingId(invoice.getRenting().getRentingId());
	}

	private List<ListInvoiceDto> idCorrectionGetAll(List<Invoice> result, List<ListInvoiceDto> response) {

		for (int i = 0; i < result.size(); i++) {
			response.get(i).setCustomerId(result.get(i).getCustomer().getUserId());
		}
		for (int i = 0; i < result.size(); i++) {
			response.get(i).setRentingId(result.get(i).getRenting().getRentingId());
		}
		return response;

	}

	public void checkIfInvoiceExists(int id) throws BusinessException {
		
		Invoice invoice =this.invoiceDao.getByInvoiceId(id);
		
		if(invoice==null) {
			throw new BusinessException(Messages.INVOICENOTFOUND);
		}
	}
	
	private void checkIfCustomerExists(int customerId) throws BusinessException {
		
		if(this.customerService.getByCustomerId(customerId)==null) {
			throw new BusinessException(Messages.CUSTOMERNOTFOUND);
		}

	}

	private void checkIfRentingExists(int rentingId) throws BusinessException {
		
		if(this.customerService.getByCustomerId(rentingId)==null) {
			throw new BusinessException(Messages.RENTINGNOTFOUND);
		}
	}
	
	private void checkInvoiceIfRentingExists(int rentalCarId) throws BusinessException {
		
		if(this.invoiceDao.getByRenting_rentingId(rentalCarId)!=null) {
			throw new BusinessException(Messages.INVOICEFOUNDBYRENTINGID);
		}	
	}

	
	private void checkIfInvoiceNoExists(String invoiceNo) throws BusinessException {
		
		if(this.invoiceDao.existsByInvoiceNo(invoiceNo)) {
			throw new BusinessException(Messages.INVOICENUMBERAlREADYEXISTS);
		}
	}

	private void invoiceTableSetColumns(Invoice invoice, CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

		invoice.setCreateDate(LocalDate.now());
		invoice.setRentDate(invoice.getRenting().getRentDate());
		invoice.setReturnDate(invoice.getRenting().getReturnDate());
		invoice.setNumberDays(dateBetweenCalculator(invoice));
		invoice.setRentTotalPrice(totalPriceCalculator(invoice));
	}

	private int dateBetweenCalculator(Invoice invoice) {
		
		Long dateBetween = ChronoUnit.DAYS.between(invoice.getRentDate(), invoice.getReturnDate());
		int numberDays=dateBetween.intValue();
		if(numberDays==0) {
			numberDays=1;
		}
		return numberDays;
	}

	private double totalPriceCalculator(Invoice invoice) throws BusinessException {
		
		double rentDailyPrice = invoice.getRenting().getTotalPrice();
		int numberDays = invoice.getNumberDays();
		double rentPrice = rentDailyPrice*numberDays;
		
		double additionalServiceDailyPrice=0;
		List<OrderedAdditionalServiceByRentingIdDto> listOrderedAdditionalServiceDtos = this.orderedAdditionalServiceService.getAllByRentingId(invoice.getRenting().getRentingId()).getData();
		
		for (int i = 0; i < listOrderedAdditionalServiceDtos.size(); i++) {	
			AdditionalServiceDto additionalService = this.additionalServiceService.getById(listOrderedAdditionalServiceDtos.get(i).getAdditionalServiceId()).getData();
			additionalServiceDailyPrice+=additionalService.getDailyPrice();	
		}
		double additionalServicePrice = additionalServiceDailyPrice*numberDays;
		
		return additionalServicePrice+rentPrice;
	}


}
