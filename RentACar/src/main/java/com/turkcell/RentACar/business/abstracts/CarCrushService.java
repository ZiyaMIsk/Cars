package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.carCrush.CarCrushDto;
import com.turkcell.RentACar.business.dtos.carCrush.ListCarCrushDto;
import com.turkcell.RentACar.business.requests.create.CreateCarCrushRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteCarCrushRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarCrushRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface CarCrushService {
	
	Result add(CreateCarCrushRequest createCarAccidentRequest);
	
	Result update(UpdateCarCrushRequest updateCarAccidentRequest);
	
	Result delete(DeleteCarCrushRequest deleteCarAccidentRequest);
	
	DataResult <List<ListCarCrushDto>> getAll();
	
	DataResult <List<ListCarCrushDto>> getCarAccidentsByCarId(int carId);
	
	DataResult <CarCrushDto> getCarAccidentsById(int carAccidentId);

}
