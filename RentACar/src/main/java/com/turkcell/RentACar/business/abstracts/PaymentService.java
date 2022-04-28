package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.payment.ListPaymentDto;
import com.turkcell.RentACar.business.dtos.payment.PaymentDto;
import com.turkcell.RentACar.business.requests.create.CreatePaymentRequest;
import com.turkcell.RentACar.business.requests.delete.DeletePaymentRequest;
import com.turkcell.RentACar.business.requests.update.UpdatePaymentRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface PaymentService {

	
	Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException;
	Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException;
	Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException;

	
	DataResult<ListPaymentDto> getByInvoiceId(int rentingId);

	boolean checkPaymentExists(int paymentId) throws BusinessException;
	DataResult<PaymentDto> getByPaymentId(int paymentId) throws BusinessException;
	DataResult<List<ListPaymentDto>> getAll();	
}
