package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.RentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.RentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorDataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.RentACar.entities.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService) {
		this.carMaintenanceDao=carMaintenanceDao;
		this.modelMapperService=modelMapperService;
	}
	
	@Override     
	public DataResult<List<ListCarMaintenanceDto>> listAll() { 
		List<CarMaintenance> carMaintenanceList = this.carMaintenanceDao.findAll();  
		if (!checkIfCarMaintenanceListEmpty(carMaintenanceList).isSuccess()) {
			return new ErrorDataResult<List<ListCarMaintenanceDto>>(checkIfCarMaintenanceListEmpty(carMaintenanceList).getMessage());
		}
		List<ListCarMaintenanceDto> response = carMaintenanceList.stream()                 
				.map(carMaintenance -> modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))    
				.collect(Collectors.toList());          
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response);   
		
		}

	@Override    
	public Result create(CreateCarMaintenanceRequest createCarMaintananceRequest) {  
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintananceRequest,     
				CarMaintenance.class);   
		if (!checkCarMaintenanceId(carMaintenance.getId()).isSuccess()) {
			return new ErrorResult(checkCarMaintenanceId(carMaintenance.getId()).getMessage());
		}
		                 
		this.carMaintenanceDao.save(carMaintenance);         
		return new SuccessResult("eklendi.");    
		}

	
	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
		if (!checkCarMaintenanceId(updateCarMaintenanceRequest.getCarId()).isSuccess()) {
			return new ErrorResult(checkCarMaintenanceId(updateCarMaintenanceRequest.getCarId()).getMessage());
		}
		if (!checkCarMaintenanceId(updateCarMaintenanceRequest.getId()).isSuccess()) {
			return new ErrorResult(checkCarMaintenanceId(updateCarMaintenanceRequest.getId()).getMessage());
		}
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessDataResult<UpdateCarMaintenanceRequest>(updateCarMaintenanceRequest, "Data updated to: " + carMaintenance.getId());
	}

	@Override
	public Result delete(int carMaintenanceId) {
		if (!checkCarMaintenanceId(carMaintenanceId).isSuccess()) {
			return new ErrorResult(checkCarMaintenanceId(carMaintenanceId).getMessage());
		}
		
		this.carMaintenanceDao.deleteById(carMaintenanceId);
		return new SuccessResult("Data deleted.");
	}

	
	
	private Result checkCarMaintenanceId(int carMaintenanceId) {
		if (!this.carMaintenanceDao.existsById(carMaintenanceId)) {
			return new ErrorResult("This maintenance id is undefined!");
		}
		return new SuccessResult();
	}
	
	private Result checkIfCarMaintenanceListEmpty(List<CarMaintenance> carMaintenances) {
		if (carMaintenances.isEmpty()) {
			return new ErrorDataResult<List<CarMaintenance>>("There is no car in maintenance exists in the list!");
		}
		return new SuccessResult();
	}

}
