package com.turkcell.RentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.RentACar.business.abstracts.CarCrushService;
import com.turkcell.RentACar.business.dtos.carCrush.CarCrushDto;
import com.turkcell.RentACar.business.dtos.carCrush.ListCarCrushDto;
import com.turkcell.RentACar.business.requests.create.CreateCarCrushRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteCarCrushRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarCrushRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/carCrushes")
public class CarCrushesController {

	private CarCrushService carAccidentService;

	public CarCrushesController(CarCrushService carAccidentService) {
		this.carAccidentService = carAccidentService;
	}
	
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarCrushRequest createCarAccidentRequest) {
		return this.carAccidentService.add(createCarAccidentRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarCrushRequest updateCarAccidentRequest) {
		return this.carAccidentService.update(updateCarAccidentRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarCrushRequest deleteCarAccidentRequest) {
		return this.carAccidentService.delete(deleteCarAccidentRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCarCrushDto>> getAll() {
		return this.carAccidentService.getAll();
	}
	
	@GetMapping("/getcaraccidentbyid")
	public DataResult<CarCrushDto> getById(@RequestParam @Valid int carAccidentId) {
		return this.carAccidentService.getCarAccidentsById(carAccidentId);
	}
	
	@GetMapping("/getcaraccidentsbycarid")
	public DataResult<List<ListCarCrushDto>> getCarAccidentsByCarId(@RequestParam @Valid int carId) throws BusinessException {
		return this.carAccidentService.getCarAccidentsByCarId(carId);
	}
}
