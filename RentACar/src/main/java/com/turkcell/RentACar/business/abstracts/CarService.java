package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.car.CarByIdDto;
import com.turkcell.RentACar.business.dtos.car.ListCarDto;
import com.turkcell.RentACar.business.requests.create.CreateCarRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.entities.Car;

public interface CarService {
	
	DataResult<List<ListCarDto>> listAll();
	
	Result create(CreateCarRequest createCarRequest) throws BusinessException;
	
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	
	Result delete(int carId);
	
	DataResult<CarByIdDto> getById(int carId);
	
	DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize);
	
	DataResult<List<ListCarDto>> getAllSorted();
	
	DataResult<List<ListCarDto>> findByDailyPriceLessThanEqual(double dailyPrice);
	
	boolean checkIfExistByCarId(int carId) throws BusinessException;
	
	public Car getByIdForOtherServices(int carId);
	
	void toSetCarKilometerValue(int carId, long kilometerValue);

	void updateRentingStatus(int carId, boolean status);
	
	void updateCarMaintenanceStatus(int carId, boolean status);

	void setCarKilometer(int carId, double endKilometer);
	
}
