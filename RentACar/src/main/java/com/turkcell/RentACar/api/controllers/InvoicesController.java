package com.turkcell.RentACar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.RentACar.business.abstracts.InvoiceService;
import com.turkcell.RentACar.business.dtos.invoice.InvoiceDto;
import com.turkcell.RentACar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.RentACar.business.requests.create.CreateInvoiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	private InvoiceService invoiceService;
	
	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException{
		
		return this.invoiceService.add(createInvoiceRequest);
		
	}
	
	@DeleteMapping("/delete")
	Result delete(@RequestParam("InvoiceId") int id) throws BusinessException{
		
		return this.invoiceService.delete(id);
		
	}
	
	@GetMapping("/getall")
	DataResult<List<ListInvoiceDto>> getAll(){
		
		return this.invoiceService.getAll();
		
	}
	
	@PutMapping("/update")
	Result update(@RequestParam("InvoiceId") int id, @RequestBody UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException{
		
		return this.invoiceService.update(id, updateInvoiceRequest);
		
	}
	
	@GetMapping("/getbyid")
	DataResult<InvoiceDto> getByInvoiceId(@RequestParam("InvoiceId") int invoiceId) throws BusinessException{
		
		return this.invoiceService.getByInvoiceId(invoiceId);
		
	}
	
	@GetMapping("/getbydate")
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finishDate){
		
		return this.invoiceService.getByDateOfBetween(startDate, finishDate);
	
	}
	
	@GetMapping("/getbycustomer")
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(@RequestParam int id) {
		
		return this.invoiceService.getInvoiceByCustomer(id);
		
	}

}
