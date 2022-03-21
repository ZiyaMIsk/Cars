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

import com.turkcell.RentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenanceByIdDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenancesInCarDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.RentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {

	private CarMaintenanceService carMaintenanceService;
	
	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) throws BusinessException  {
		this.carMaintenanceService=carMaintenanceService;
	}
	
	@GetMapping("/listall")
	public DataResult<List<ListCarMaintenanceDto>> listAll() throws BusinessException {
		return this.carMaintenanceService.listAll();
	}
	
	@PostMapping("/create")
	public Result create(@RequestBody  @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.create(createCarMaintenanceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("carMaintenanceId") int carMaintenanceId, @RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.update(carMaintenanceId, updateCarMaintenanceRequest);
	}
	
	@DeleteMapping("/delete")
    public Result delete(@RequestParam("carMaintenanceId") int carMaintenanceId) throws BusinessException {
		return this.carMaintenanceService.delete(carMaintenanceId);
    }
	
	@GetMapping("/getCarMaintenanceById")
	public DataResult<CarMaintenanceByIdDto> getById(@RequestParam @Valid int carMaintenanceId) throws BusinessException{
		return this.carMaintenanceService.getById(carMaintenanceId);
	}
	
	@GetMapping("/getCarMaintenancesByCarId")
	public DataResult<List<CarMaintenancesInCarDto>> getCarMaintenancesByCarId(@RequestParam @Valid int carId)  throws BusinessException {
		return this.carMaintenanceService.getCarMaintenancesByCarId(carId);
	}
	
}
