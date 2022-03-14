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

import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.dtos.renting.ListRentingDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByCarIdDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByIdDto;
import com.turkcell.RentACar.business.requests.create.CreateRentingRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteRentingRequest;
import com.turkcell.RentACar.business.requests.update.UpdateRentingRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/rentings")
public class RentingsController {
	private RentingService rentingService;

    @Autowired
    public RentingsController(RentingService rentingService){
        this.rentingService = rentingService;
    }
    @GetMapping("/listallrentals")
    public DataResult<List<ListRentingDto>> listAll() {
        return this.rentingService.getAll();
    }
    @PostMapping("/createrenting")
    public Result create(@RequestBody @Valid CreateRentingRequest createRentingRequest) throws BusinessException{
        return this.rentingService.create(createRentingRequest);
    }
    @DeleteMapping("/deleterenting")
    public Result delete(@RequestBody @Valid DeleteRentingRequest deleteRentingRequest) throws BusinessException{
        return this.rentingService.delete(deleteRentingRequest);
    }
    @PutMapping("/updaterenting")
    public Result update(@RequestBody @Valid UpdateRentingRequest updateRentingRequest) throws BusinessException{
        return this.rentingService.update(updateRentingRequest);
    }


	@GetMapping("/getrentalcarbycarid/{carId}")
	public DataResult<List<RentingByCarIdDto>> getRentalCarByCarId(@RequestParam("carId") @Valid int carId) throws BusinessException{
		return this.rentingService.getRentingByCarId(carId);
	}
	
	@GetMapping("/getrentalcarbyid/{rentalcarid}")
	public DataResult<RentingByIdDto> getRentalCarById(@RequestParam("rentingId") @Valid int rentingId) throws BusinessException{
		return this.rentingService.getRentingById(rentingId);
	}
	
	
}
