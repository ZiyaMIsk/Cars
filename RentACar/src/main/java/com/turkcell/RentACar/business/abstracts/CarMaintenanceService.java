package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.RentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface CarMaintenanceService {
	DataResult<List<ListCarMaintenanceDto>> listAll();
	Result create(CreateCarMaintenanceRequest createCarMaintenanceRequest);
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);
	Result delete(int carMaintenanceId);
	

}
