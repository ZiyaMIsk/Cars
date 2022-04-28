package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.carCrush.ListCarCrushDto;
import com.turkcell.RentACar.business.requests.create.CreateCarCrushRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteCarCrushRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarCrushRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;


public interface CarCrushService {
	
	Result add(CreateCarCrushRequest createCarAccidentRequest) throws BusinessException;
	
	Result update(UpdateCarCrushRequest updateCarAccidentRequest) throws BusinessException;
	
	Result delete(DeleteCarCrushRequest deleteCarAccidentRequest) throws BusinessException;
	
	DataResult <List<ListCarCrushDto>> getAll();
	
	DataResult <List<ListCarCrushDto>> getCarAccidentsByCarId(int carId);
	
	DataResult<ListCarCrushDto> getCarAccidentsById(int carAccidentId) throws BusinessException;

	boolean checkCarCrushExists(int carCrushId) throws BusinessException;

}
