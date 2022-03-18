package com.turkcell.RentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.RentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.RentACar.business.dtos.corparateCustomer.ListCorporateCustomerDto;
import com.turkcell.RentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteCorporateCustomerRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;



@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {
	
	CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest){
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest){
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest){
		return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		return this.corporateCustomerService.getAll();
	}

}
