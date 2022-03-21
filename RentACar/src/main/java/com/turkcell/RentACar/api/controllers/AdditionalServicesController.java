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

import com.turkcell.RentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.RentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.RentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/additionalServices")
public class AdditionalServicesController {
   
	private AdditionalServiceService additionalServiceService;

    @Autowired
    public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
        this.additionalServiceService = additionalServiceService;
    }

    @GetMapping("/listalladditionalservices")
    public DataResult<List<ListAdditionalServiceDto>> listAll() {
        return this.additionalServiceService.listAll();
    }
    
    @PostMapping("/createadditionalservice")
    public Result create(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest){
        return this.additionalServiceService.create(createAdditionalServiceRequest);
    }
    
    @DeleteMapping("/deleteadditionalservice")
    public Result delete(@RequestParam("additionalServiceId") int id){
        return this.additionalServiceService.delete(id);
    }
    
    @PutMapping("/updateadditionalservice")
    public Result update(@RequestParam("additionalServiceId") int id, @RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest){
        return this.additionalServiceService.update(id, updateAdditionalServiceRequest);
    }

    @GetMapping("/getbyadditionalserviceid")
    public DataResult<AdditionalServiceDto> getById(@RequestParam("additionalServiceId") @Valid int additionalServiceId){
        return this.additionalServiceService.getById(additionalServiceId);
    }

}
