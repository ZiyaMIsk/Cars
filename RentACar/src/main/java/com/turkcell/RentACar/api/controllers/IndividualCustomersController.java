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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.RentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.RentACar.business.dtos.individualCustomer.IndividualCustomerDto;
import com.turkcell.RentACar.business.dtos.individualCustomer.ListIndividualCustomerDto;
import com.turkcell.RentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteIndividualCustomerRequest;
import com.turkcell.RentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

	IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException{
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("individualCustomerId") int id, @RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException{
		return this.individualCustomerService.update(id, updateIndividualCustomerRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest){
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		return this.individualCustomerService.getAll();
	}
	
	@GetMapping("/getid/{individualCustomerId}")
	DataResult<IndividualCustomerDto> getById(@RequestParam("individualCustomerId") int id) throws BusinessException {
		return this.individualCustomerService.getById(id);
	}
	
}
