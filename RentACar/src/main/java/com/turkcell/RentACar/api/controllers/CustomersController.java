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

import com.turkcell.RentACar.business.abstracts.CustomerService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalServicesController")
public class CustomersController {

    private CustomerService customerService;

    @Autowired
    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/getAll")
    DataResult<List<CustomerListDto>> getAll(){
        return this.customerService.listAll();
    }

    @PostMapping("/add")
    Result add(@RequestBody  @Valid CreateCustomerRequest createCustomerRequest){
        return this.customerService.add(createCustomerRequest);
    }

    @GetMapping("/getById")
    DataResult<CustomerDto> getById(@RequestParam int id){
        return this.customerService.getById(id);
    }

    @PutMapping("/update")
    Result update(@RequestParam @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest){
        return this.customerService.update(updateCustomerRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody @Valid DeleteCustomerRequest deleteCustomerRequest){
        return this.customerService.delete(deleteCustomerRequest);
    }

}
