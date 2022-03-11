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
import com.turkcell.RentACar.business.dtos.renting.RentingDto;
import com.turkcell.RentACar.business.requests.create.CreateRentingRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteRentingRequest;
import com.turkcell.RentACar.business.requests.update.UpdateRentingRequest;
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
        return this.rentingService.listAll();
    }
    @PostMapping("/createrental")
    public Result create(@RequestBody @Valid CreateRentingRequest createRentingRequest){
        return this.rentingService.create(createRentingRequest);
    }
    @DeleteMapping("/deleterental")
    public Result delete(@RequestBody @Valid DeleteRentingRequest deleteRentingRequest){
        return this.rentingService.delete(deleteRentingRequest);
    }
    @PutMapping("/updaterental")
    public Result update(@RequestBody @Valid UpdateRentingRequest updateRentingRequest){
        return this.rentingService.update(updateRentingRequest);
    }

    @GetMapping("/getbyrentalid")
    public DataResult<RentingDto> getById(@RequestParam int rentalId){
        return this.rentingService.getById(rentalId);
    }
	
	
	
}
