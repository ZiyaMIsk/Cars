package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenanceByIdDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenancesInCarDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.RentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface CarMaintenanceService {
	
	DataResult<List<ListCarMaintenanceDto>> listAll();
	
	Result create(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
	
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
	
	Result delete(int carMaintenanceId);
	
	DataResult<List<CarMaintenancesInCarDto>> getCarMaintenancesByCarId(int carId);
	
	DataResult<CarMaintenanceByIdDto> getById(int carMaintenanceId) throws BusinessException;
	
	boolean checkIfCarNotInMaintenance(int carId) throws BusinessException;

}
