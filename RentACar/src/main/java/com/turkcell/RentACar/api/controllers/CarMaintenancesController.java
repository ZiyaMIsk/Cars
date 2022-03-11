package com.turkcell.RentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.RentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.RentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.RentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {

	private CarMaintenanceService carMaintenanceService;
	
	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService=carMaintenanceService;
	}
	
	@GetMapping("/listall")
	public DataResult<List<ListCarMaintenanceDto>> listAll(){
		return this.carMaintenanceService.listAll();
	}
	
	@PostMapping("/create")
	public Result create(@RequestBody CreateCarMaintenanceRequest createCarMaintenanceRequest){
		return this.carMaintenanceService.create(createCarMaintenanceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}
	
	@DeleteMapping("/delete")
    public Result delete(@RequestBody int carMaintenanceId){
		return this.carMaintenanceService.delete(carMaintenanceId);
    }
	
	
}
