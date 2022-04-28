package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CarCrushService;
import com.turkcell.RentACar.business.abstracts.CarService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.carCrush.ListCarCrushDto;
import com.turkcell.RentACar.business.requests.create.CreateCarCrushRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteCarCrushRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarCrushRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.CarCrashDao;
import com.turkcell.RentACar.entities.CarCrush;

@Service
public class CarCrushManager implements CarCrushService{

	private CarCrashDao carDamageDao;
	private ModelMapperService modelMapperService;
	private CarService carService;	
	
	
	@Autowired
	public CarCrushManager(CarCrashDao carDamageDao, ModelMapperService modelMapperService, CarService carService) {
		
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public Result add(CreateCarCrushRequest createCarCrushRequest) throws BusinessException {

		this.carService.checkIfExistByCarId(createCarCrushRequest.getCarId());
		
		CarCrush carCrush = this.modelMapperService.forRequest().map(createCarCrushRequest, CarCrush.class);
		this.carDamageDao.save(carCrush);
		
		return new SuccessResult(Messages.CARCRUSHADDED);
	}

	@Override
	public Result update(UpdateCarCrushRequest updateCarAccidentRequest) throws BusinessException {
	
		
		this.carService.checkIfExistByCarId(updateCarAccidentRequest.getCarId());		
		checkCarCrushExists(updateCarAccidentRequest.getCarCrushId());
		
		CarCrush carDamage = this.modelMapperService.forRequest().map(updateCarAccidentRequest, CarCrush.class);
		this.carDamageDao.save(carDamage);
		
		return new SuccessResult(Messages.CARCRUSHUPDATED);	
		
	}
	

	@Override
	public Result delete(DeleteCarCrushRequest deleteCarAccidentRequest) throws BusinessException {
		checkCarCrushExists(deleteCarAccidentRequest.getCarCrushId());
		
		CarCrush carCrush = this.modelMapperService.forRequest().map(deleteCarAccidentRequest, CarCrush.class);
		this.carDamageDao.deleteById(carCrush.getCarCrushId());

		return new SuccessResult(Messages.CARCRUSHDELETED);
	}

	@Override
	public DataResult<List<ListCarCrushDto>> getAll() {
		
		List<CarCrush> result = this.carDamageDao.findAll();
		List<ListCarCrushDto> response = result.stream().map(carCrush -> this.modelMapperService.forDto().map(carCrush, ListCarCrushDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarCrushDto>>(response, Messages.CARCRUSHLISTED);
	}

	@Override
	public DataResult<List<ListCarCrushDto>> getCarAccidentsByCarId(int carId) {
		
		List<CarCrush> result = this.carDamageDao.getByCar_CarId(carId);
		
		List<ListCarCrushDto> response = result.stream().map(payment -> this.modelMapperService.forDto().map(payment, ListCarCrushDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarCrushDto>>(response,Messages.CARCRUSHFOUND);
	}

	@Override
	public DataResult<ListCarCrushDto> getCarAccidentsById(int carAccidentId) throws BusinessException {
		checkCarCrushExists(carAccidentId);
		
		CarCrush result = this.carDamageDao.getById(carAccidentId);			
		ListCarCrushDto response = this.modelMapperService.forDto().map(result, ListCarCrushDto.class);	
		
		return new SuccessDataResult<ListCarCrushDto>(response,Messages.CARCRUSHFOUND);
	}
	
	@Override
	public boolean checkCarCrushExists(int carCrushId) throws BusinessException {
		
		var result = this.carDamageDao.existsById(carCrushId);		
		if (result) {
			return true;
		}
		throw new BusinessException(Messages.CARCRUSHNOTFOUND);
	}
}
