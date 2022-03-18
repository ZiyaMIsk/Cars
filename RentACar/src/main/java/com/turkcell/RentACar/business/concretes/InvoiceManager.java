package com.turkcell.RentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CustomerService;
import com.turkcell.RentACar.business.abstracts.InvoiceService;
import com.turkcell.RentACar.business.dtos.customer.CustomerDto;
import com.turkcell.RentACar.business.dtos.invoice.InvoiceDto;
import com.turkcell.RentACar.business.dtos.invoice.ListInvoiceDto;
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

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, CustomerService customerService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
	
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		
		CustomerDto customer = this.customerService.getById(createInvoiceRequest.getCustomerId()).getData();
		
		Customer c = this.modelMapperService.forDto().map(customer, Customer.class);
		
		invoice.setCustomer(c);
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Added");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		this.invoiceDao.deleteById(id);
		
		return new SuccessResult("Invoice.Deleted");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		var result = this.invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response,"Success");
	}

	@Override
	public Result update(int id, UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		
		Invoice invoice = this.invoiceDao.getByInvoiceId(id);
		updateOperation(invoice,updateInvoiceRequest);
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Updated");
	}


	@Override
	public DataResult<InvoiceDto> getByInvoiceId(int invoiceId) throws BusinessException {
		
		Invoice result = this.invoiceDao.getByInvoiceId(invoiceId);
		if (result != null) {
			InvoiceDto response = this.modelMapperService.forDto().map(result,InvoiceDto.class);
			
			return new SuccessDataResult<InvoiceDto>(response, "Success");
		}
		return new ErrorDataResult<InvoiceDto>("Cannot find a color with this Id.");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {
		List<Invoice> result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {
		List<Invoice> result = this.invoiceDao.getByCustomer_customerId(id);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	private void updateOperation(Invoice invoice, UpdateInvoiceRequest updateInvoiceRequest) {
		invoice.setCreateDate(updateInvoiceRequest.getCreateDate());;
		
	}


}
