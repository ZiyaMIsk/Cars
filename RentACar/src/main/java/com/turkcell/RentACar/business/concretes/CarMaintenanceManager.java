package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.RentACar.business.abstracts.CarService;
import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenanceByIdDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenancesInCarDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.RentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
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
	private CarService carService;
	private RentingService rentingService;
	

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, @Lazy CarService carService, @Lazy RentingService rentalCarService) {
		
		this.carMaintenanceDao=carMaintenanceDao;
		this.modelMapperService=modelMapperService;
		this.carService = carService;
		
	}
	
	@Override     
	public DataResult<List<ListCarMaintenanceDto>> listAll() { 
		
		List<CarMaintenance> carMaintenanceList = this.carMaintenanceDao.findAll();  
		
		if (!checkIfCarMaintenanceListEmpty(carMaintenanceList).isSuccess()) {
			
			return new ErrorDataResult<List<ListCarMaintenanceDto>>(checkIfCarMaintenanceListEmpty(carMaintenanceList).getMessage());
		
		}
		
		List<ListCarMaintenanceDto> response = carMaintenanceList.stream().map(carMaintenance -> modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());          
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response);   
		
		}

	@Override    
	public Result create(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {  
		
		this.carService.checkIfExistByCarId(createCarMaintenanceRequest.getCarId());
		this.rentingService.checkIfCarNotInRent(createCarMaintenanceRequest.getCarId());
		
		checkIfCarNotInMaintenance(createCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);   
		                
		this.carMaintenanceDao.save(carMaintenance);         
		
		return new SuccessResult("eklendi.");    
		
	}

	
	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		
		this.carService.checkIfExistByCarId(updateCarMaintenanceRequest.getCarId());
		checkIfCarInMaintenance(updateCarMaintenanceRequest.getCarId()); 
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setCarMaintenanceId(this.carMaintenanceDao.getByReturnDateAndCarMaintenanceCar_CarId(null, updateCarMaintenanceRequest.getCarId()).getCarMaintenanceId());
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult("CarMaintenance.Updated");
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

	@Override
	public DataResult<List<CarMaintenancesInCarDto>> getCarMaintenancesByCarId(int carId) {
		var result = this.carMaintenanceDao.getByCarMaintenanceCar_CarId(carId);
		List<CarMaintenancesInCarDto> response = result.stream().map(carMaintenance->this.modelMapperService.forDto()
				.map(carMaintenance, CarMaintenancesInCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult <List<CarMaintenancesInCarDto>>(response);
	}

	@Override
	public DataResult<CarMaintenanceByIdDto> getById(int carMaintenanceId) throws BusinessException {
		checkIfExistByCarMaintenanceId(carMaintenanceId);
		var result = this.carMaintenanceDao.findByCarMaintenanceId(carMaintenanceId);
		CarMaintenanceByIdDto response = this.modelMapperService.forDto().map(result, CarMaintenanceByIdDto.class);
		return new SuccessDataResult<CarMaintenanceByIdDto> (response);
	}

	private boolean checkIfExistByCarMaintenanceId(int id) throws BusinessException {
		if(this.carMaintenanceDao.findByCarMaintenanceId(id) != null) {
			return true;
		}
		throw new BusinessException("The car maintenance id you wrote is not exist.");
	}
	
	@Override
	public boolean checkIfCarNotInMaintenance(int carId) throws BusinessException {
		
		if(this.carMaintenanceDao.getByReturnDateAndCarMaintenanceCar_CarId(null, carId) != null) {
			
			throw new BusinessException("The car in maintenance.");
		
		}
		
		return true;
	}
	
	private boolean checkIfCarInMaintenance(int carId)throws BusinessException {
		if(this.carMaintenanceDao.getByReturnDateAndCarMaintenanceCar_CarId(null, carId) == null) {
			throw new BusinessException("The car is not in maintenance.");
		}
		return true;	
	}

	
}
