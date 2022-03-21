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

import com.turkcell.RentACar.business.abstracts.CarService;
import com.turkcell.RentACar.business.dtos.car.CarByIdDto;
import com.turkcell.RentACar.business.dtos.car.ListCarDto;
import com.turkcell.RentACar.business.requests.create.CreateCarRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;



@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private CarService carService;

	@Autowired
	public CarsController(CarService carService) {
		this.carService = carService;
	}
	
	@GetMapping("/listall")
	public DataResult<List<ListCarDto>> listAll(){
		return this.carService.listAll();
	}
	
	@PostMapping("/create")
	public Result create(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException{
		return this.carService.create(createCarRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("carId") int carId, @RequestBody @Valid UpdateCarRequest updateCarRequest) throws BusinessException{
		return this.carService.update(updateCarRequest);
	}
		
	@DeleteMapping("/deletecar")
    public Result delete(@RequestParam("carId") int carId){
		return this.carService.delete(carId);
    }
	
	@GetMapping("/getbyid")
	public DataResult<CarByIdDto> getById(@RequestParam @Valid int carId){
		return this.carService.getById(carId);
	}
	
	
	@GetMapping("/getAllPaged")
	DataResult<List<ListCarDto>> getAllPaged(@RequestParam @Valid int pageNo, int pageSize) {
		return this.carService.getAllPaged(pageNo, pageSize);

	}

	@GetMapping("/getAllSorted")
	DataResult<List<ListCarDto>> getAllSorted() {
		return this.carService.getAllSorted();

	}

	@GetMapping("/getAllByDailyPriceLessThanEqual")
	DataResult<List<ListCarDto>> findByDailyPriceLessThanEqual(@RequestParam @Valid double dailyPrice) {
		return this.carService.findByDailyPriceLessThanEqual(dailyPrice);

	}
	
}
