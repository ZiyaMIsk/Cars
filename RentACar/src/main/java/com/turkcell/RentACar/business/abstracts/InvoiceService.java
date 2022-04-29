package com.turkcell.RentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.RentACar.business.dtos.invoice.InvoiceDto;
import com.turkcell.RentACar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.RentACar.business.requests.create.CreateInvoiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface InvoiceService {

	Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

	Result delete(int id) throws BusinessException;

	DataResult<List<ListInvoiceDto>> getAll();
	
	Result update(int id,UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;

	DataResult<InvoiceDto> getByInvoiceId(int invoiceId) throws BusinessException;
	
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (LocalDate startDate, LocalDate finishDate);
	
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id);

	void checkIfInvoiceExists(int invoiceId) throws BusinessException;


}
