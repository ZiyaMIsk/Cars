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
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.ListOrderedAdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByIdDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByRentingIdDto;
import com.turkcell.RentACar.business.requests.create.CreateOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalServicesController")
public class OrderedAdditionalServiceController {
	  
		OrderedAdditionalServiceService orderedAdditionalServiceService;

	    @Autowired
	    public OrderedAdditionalServiceController (OrderedAdditionalServiceService orderedAdditionalServiceService) {
	        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	    }

	    @GetMapping("/getAll")
	    DataResult<List<ListOrderedAdditionalServiceDto>> getAll(){
	        return this.orderedAdditionalServiceService.getAll();
	    }

	    @PostMapping("/add")
	    Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException{
	        return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	    }

	    @GetMapping("/getById")
	    DataResult<OrderedAdditionalServiceByIdDto> getById(@RequestParam @Valid int id) throws BusinessException{
	        return this.orderedAdditionalServiceService.getById(id);
	    }

	    @PutMapping("/update")
	    Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException{
	        return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
	    }

	    @DeleteMapping("/delete")
	    Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException{
	        return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	    }
	    
	    @GetMapping("/getallbyrentingid/{rentingid}")
		public DataResult<List<OrderedAdditionalServiceByRentingIdDto>> getAllByRentalCarId(@RequestParam ("rentingId") int rentingId) throws BusinessException{
			return this.orderedAdditionalServiceService.getAllByRentingId(rentingId);
		}
}
