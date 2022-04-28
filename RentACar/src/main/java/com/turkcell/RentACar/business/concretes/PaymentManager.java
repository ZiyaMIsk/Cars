package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CreditCardService;
import com.turkcell.RentACar.business.abstracts.InvoiceService;
import com.turkcell.RentACar.business.abstracts.PaymentService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.payment.ListPaymentDto;
import com.turkcell.RentACar.business.dtos.payment.PaymentDto;
import com.turkcell.RentACar.business.requests.create.CreatePaymentRequest;
import com.turkcell.RentACar.business.requests.delete.DeletePaymentRequest;
import com.turkcell.RentACar.business.requests.update.UpdatePaymentRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.RentACar.entities.Payment;

@Service
public class PaymentManager implements PaymentService{

	private ModelMapperService modelMapperService;
	private PaymentDao paymentDao;
	private InvoiceService invoiceService;
	
	private CreditCardService creditCardService;

	public PaymentManager(ModelMapperService modelMapperService, PaymentDao paymentDao, InvoiceService invoiceService, CreditCardService creditCardService) {
		
		this.modelMapperService = modelMapperService;
		this.paymentDao = paymentDao;
		this.invoiceService = invoiceService;
		
		this.creditCardService = creditCardService;
	
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException {
		
		this.invoiceService.checkRentCarExists(createPaymentRequest.getInvoiceId());
		
		checkRememberMe(createPaymentRequest);
		
			
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setTotalPayment(calculatorTotalPrice(createPaymentRequest.getInvoiceId()));
		payment.setPaymentId(0);
		
		this.paymentDao.save(payment);

		return new SuccessResult(Messages.PAYMENTADDED);
	}

	@Override
	public Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
		
		checkPaymentExists(deletePaymentRequest.getPaymentId());

		Payment payment = this.modelMapperService.forRequest().map(deletePaymentRequest, Payment.class);
		this.paymentDao.deleteById(payment.getPaymentId());

		return new SuccessResult(Messages.PAYMENTDELETED);
	}


	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException {

		checkPaymentExists(updatePaymentRequest.getPaymentId());		
		return null;
	}
	
	@Override
	public DataResult<List<ListPaymentDto>> getAll(){
		
		List<Payment> result = this.paymentDao.findAll();
		
		List<ListPaymentDto> response = result.stream().map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListPaymentDto>>(response, Messages.PAYMENTSLISTED);
	}
	
	@Override
	public DataResult<PaymentDto> getByPaymentId(int paymentId) throws BusinessException {
		
		checkPaymentExists(paymentId);
		
		Payment result = this.paymentDao.getById(paymentId);
		
		PaymentDto response = this.modelMapperService.forDto().map(result, PaymentDto.class);
		
		return new SuccessDataResult<PaymentDto>(response, Messages.PAYMENTFOUND);
	}

	@Override
	public DataResult<ListPaymentDto> getByInvoiceId(int rentingId) {
		
		this.invoiceService.checkRentCarExists(rentingId);

		var result = this.paymentDao.getAllByRenting_RentingId(rentingId);
		ListPaymentDto response = this.modelMapperService.forDto().map(result, ListPaymentDto.class);
		
		return new SuccessDataResult<ListPaymentDto>(response , Messages.PAYMENTSLISTED);
	}
	
	@Override
	public boolean checkPaymentExists(int paymentId) throws BusinessException {
		if (this.paymentDao.existsById(paymentId)) {
			return true;
		}
		throw new BusinessException(Messages.PAYMENTNOTFOUND);
	}
		
	public double calculatorTotalPrice(int rentalId) {

		var returnedRental = this.invoiceService.returnRenting(rentalId);

		return returnedRental.getTotalPrice();
	}

	public boolean checkRememberMe(CreatePaymentRequest createPaymentRequest) throws BusinessException {
		
		if (createPaymentRequest.isRememberMe()) {
			this.creditCardService.save(createPaymentRequest.getCreateCreditCardRequest());
		}
		return true;
	}
	

}

