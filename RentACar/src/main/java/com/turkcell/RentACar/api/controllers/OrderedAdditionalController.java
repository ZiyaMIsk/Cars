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

import com.turkcell.RentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.RentACar.business.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalServicesController")
public class OrderedAdditionalController {
	  
		private OrderedAdditionalServiceService orderedAdditionalServiceService;

	    @Autowired
	    public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
	        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	    }

	    @GetMapping("/getAll")
	    DataResult<List<OrderedAdditionalServiceListDto>> getAll(){
	        return this.orderedAdditionalServiceService.getAll();
	    }

	    @PostMapping("/add")
	    Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest){
	        return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	    }

	    @GetMapping("/getById")
	    DataResult<OrderedAdditionalServiceDto> getById(@RequestParam int id){
	        return this.orderedAdditionalServiceService.getById(id);
	    }

	    @PutMapping("/update")
	    Result update(@RequestParam @Valid int id,@RequestBody UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest){
	        return this.orderedAdditionalServiceService.update(id,updateOrderedAdditionalServiceRequest);
	    }

	    @DeleteMapping("/delete")
	    Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest){
	        return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	    }

}
