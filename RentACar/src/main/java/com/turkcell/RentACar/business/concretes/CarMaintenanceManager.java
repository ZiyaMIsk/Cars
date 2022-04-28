package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.RentACar.business.abstracts.CarService;
import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.car.CarByIdDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenanceByIdDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenancesInCarDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByCarIdDto;
import com.turkcell.RentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteCarMaintenanceRequest;
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
		
		List<CarMaintenance> result = this.carMaintenanceDao.findAll();  
		
		checkIfCarMaintenanceListEmpty(result); 
		
		List<ListCarMaintenanceDto> response = result.stream().map(carMaintenance -> modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());          

		response = idCorrection(response, result);
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response, Messages.CARMAINTENANCELISTED);   
		
		}

	@Override    
	public Result create(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {  
		
		this.carService.checkIfExistByCarId(createCarMaintenanceRequest.getCarId());
		this.rentingService.checkIfCarNotInRent(createCarMaintenanceRequest.getCarId());
	
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);   
		checkIfCarInMaintenance(carMaintenance.getCarMaintenanceId());
		
		carMaintenance.setCarMaintenanceId(0);
		
		checkIfRentingExists(carMaintenance);
		checkIfCarExists(carMaintenance.getCarMaintenanceCar().getCarId());		
		checkIfCarNotInMaintenance(createCarMaintenanceRequest.getCarId());
		
		                
		this.carMaintenanceDao.save(carMaintenance);         
		
		return new SuccessResult(Messages.CARMAINTENANCEADDED);    
		
	}

	
	@Override
	public Result update(int id, UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		
		this.carService.checkIfExistByCarId(updateCarMaintenanceRequest.getCarId());
		checkIfCarInMaintenance(updateCarMaintenanceRequest.getCarId()); 
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setCarMaintenanceId(this.carMaintenanceDao.getByReturnDateAndCarMaintenanceCar_CarId(updateCarMaintenanceRequest.getReturnDate(), updateCarMaintenanceRequest.getCarId()).getCarMaintenanceId());
		
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(Messages.CARMAINTENANCEUPDATED);
	
	}

	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) {

		checkCarMaintenanceId(deleteCarMaintenanceRequest.getCarMaintenanceId());

		this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getCarMaintenanceId());
		return new SuccessResult(Messages.CARMAINTENANCEDELETED);
	}

	@Override
	public DataResult<List<CarMaintenancesInCarDto>> getCarMaintenancesByCarId(int carId) {
		
		var result = this.carMaintenanceDao.getByCarMaintenanceCar_CarId(carId);
		
		List<CarMaintenancesInCarDto> response = result.stream().map(carMaintenance->this.modelMapperService.forDto().map(carMaintenance, CarMaintenancesInCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult <List<CarMaintenancesInCarDto>>(response, Messages.CARMAINTENANCEFOUNDBYCARID);
	
	}
	
	@Override
	public DataResult<CarMaintenanceByIdDto> getById(int carMaintenanceId) throws BusinessException {
		
		checkIfExistByCarMaintenanceId(carMaintenanceId);
		
		var result = this.carMaintenanceDao.findByCarMaintenanceId(carMaintenanceId);
		
		CarMaintenanceByIdDto response = this.modelMapperService.forDto().map(result, CarMaintenanceByIdDto.class);
		
		return new SuccessDataResult<CarMaintenanceByIdDto> (response, Messages.CARMAINTENANCEFOUND);
	
	}
	
	private Result checkCarMaintenanceId(int carMaintenanceId) {
		
		if (!this.carMaintenanceDao.existsById(carMaintenanceId)) {
			
			return new ErrorResult(Messages.CARMAINTENANCENOTFOUND);
		}
		
		return new SuccessResult();
		
	}
	
	private Result checkIfCarMaintenanceListEmpty(List<CarMaintenance> carMaintenances) {
		
		if (carMaintenances.isEmpty()) {
			return new ErrorDataResult<List<CarMaintenance>>(Messages.CARMAINTENANCENOTFOUNDATLİST);
		}
		
		return new SuccessResult();
	
	}

	private boolean checkIfExistByCarMaintenanceId(int id) throws BusinessException {
		
		if(this.carMaintenanceDao.findByCarMaintenanceId(id) != null) {
		
			return true;
		
		}
		
		throw new BusinessException(Messages.CARMAINTENANCENOTFOUND);
	}
	
	
	@Override
	public boolean checkIfCarNotInMaintenance(int carId) throws BusinessException {
		
		if(this.carMaintenanceDao.getByReturnDateAndCarMaintenanceCar_CarId(null, carId) != null) {
			
			throw new BusinessException(Messages.CARINMAINTENANCE);
		
		}
		
		return true;
	}
	
	private boolean checkIfCarInMaintenance(int carId)throws BusinessException {
		if(this.carMaintenanceDao.getByReturnDateAndCarMaintenanceCar_CarId(null, carId) == null) {
			throw new BusinessException(Messages.CARNOTINMAINTENANCE);
		}
		return true;	
	}


	private boolean checkIfRentingExists(CarMaintenance carMaintenance) throws BusinessException{
		
		DataResult<List<RentingByCarIdDto>> result = this.rentingService.getRentingByCarId(carMaintenance.getCarMaintenanceCar().getCarId());

		if (!result.isSuccess()) {
			return true;
		}
		for (RentingByCarIdDto renting : result.getData()) {

			if (renting.getReturnDate() == null) {
				throw new BusinessException(Messages.CARNOTSENDMAINTENANCEFORRENTED);
			}

		}
		return true;
	}

	private boolean checkIfCarExists(int id) throws BusinessException{

		DataResult<CarByIdDto> result = this.carService.getById(id);
		
		if (!result.isSuccess()) {
			throw new BusinessException(Messages.CARNOTFOUND);
		}
		return true;

	}

	private List<ListCarMaintenanceDto> idCorrection(List<ListCarMaintenanceDto> response, List<CarMaintenance> result) {
		
		for (int i = 0; i < result.size(); i++) {
			for (ListCarMaintenanceDto listCarMaintenanceDt : response) {
				if (result.get(i).getCarMaintenanceId() == listCarMaintenanceDt.getCarMaintenanceId()) {
					listCarMaintenanceDt.setCarId(result.get(i).getCarMaintenanceCar().getCarId());
				}
			}
		}
		return response;

	}

	
}
	

