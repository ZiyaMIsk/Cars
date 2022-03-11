package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.car.CarDto;
import com.turkcell.RentACar.business.dtos.car.ListCarDto;
import com.turkcell.RentACar.business.requests.create.CreateCarRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface CarService {
	
	DataResult<List<ListCarDto>> listAll();
	Result create(CreateCarRequest createCarRequest);
	Result update(UpdateCarRequest updateCarRequest);
	Result delete(int carId);
	DataResult<CarDto> getById(int carId);
	DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize);
	DataResult<List<ListCarDto>> getAllSorted();
	DataResult<List<ListCarDto>> findByDailyPriceLessThanEqual(double dailyPrice);

}
