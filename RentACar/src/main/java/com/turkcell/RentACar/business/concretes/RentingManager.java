package com.turkcell.RentACar.business.concretes;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.RentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.RentACar.business.abstracts.CarService;
import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.dtos.renting.ListRentingDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByCarIdDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByIdDto;
import com.turkcell.RentACar.business.requests.create.CreateRentingRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteRentingRequest;
import com.turkcell.RentACar.business.requests.update.UpdateRentingRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.RentingDao;
import com.turkcell.RentACar.entities.OrderedAdditionalService;
import com.turkcell.RentACar.entities.Renting;

@Service
public class RentingManager implements RentingService{
	private RentingDao rentingDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	
	
	
	@Autowired
	public RentingManager(RentingDao rentingDao, @Lazy CarMaintenanceService carMaintenanceService,
			@Lazy CarService carService, ModelMapperService modelMapperService, AdditionalServiceService additionalServiceService) {
		this.rentingDao = rentingDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
	
	}



	@Override
	public Result create(CreateRentingRequest createRentingRequest) throws BusinessException {
		this.carService.checkIfExistByCarId(createRentingRequest.getCarId()); 
		this.carMaintenanceService.checkIfCarNotInMaintenance(createRentingRequest.getCarId());
		checkIfCarNotInRent(createRentingRequest.getCarId());
		
		
		Renting renting = this.modelMapperService.forRequest().map(createRentingRequest, Renting.class);
		renting.setRentingId(0);
		
		if(createRentingRequest.getReturnDate() != null) {
			checkIfReturnDateAfterRentDate(createRentingRequest.getRentDate(), createRentingRequest.getReturnDate());
			double totalPrice = totalPriceCalculate(this.rentingDao.getByReturnDateAndRentedCar_carId(createRentingRequest.getReturnDate(), createRentingRequest.getCarId()));
			renting.setTotalPrice(totalPrice);
		}
		
		this.rentingDao.save(renting);
		return new SuccessResult("Renting.Added");
	}



	@Override
	public Result delete(DeleteRentingRequest deleteRentingRequest) throws BusinessException {
		checkIfExistById(deleteRentingRequest.getRentingId());
		
		this.rentingDao.deleteById(deleteRentingRequest.getRentingId());
		return new SuccessResult("RentalCar.Deleted");
	}



	@Override
	public Result update(UpdateRentingRequest updateRentingRequest) throws BusinessException {
		this.carService.checkIfExistByCarId(updateRentingRequest.getCarId());
		checkIfCarInRent(updateRentingRequest.getCarId());
		
		Renting renting = this.modelMapperService.forRequest().map(updateRentingRequest, Renting.class);
		renting.setRentingId(this.rentingDao.getByReturnDateAndRentedCar_carId(null, updateRentingRequest.getCarId()).getRentingId());
		
		if(updateRentingRequest.getReturnDate() != null) {
			checkIfReturnDateAfterRentDate(this.rentingDao.getByReturnDateAndRentedCar_carId(updateRentingRequest.getReturnDate(), updateRentingRequest.getCarId()).getRentDate(),
					updateRentingRequest.getReturnDate());
			double totalPrice = totalPriceCalculate(this.rentingDao.getByReturnDateAndRentedCar_carId(updateRentingRequest.getReturnDate(),updateRentingRequest.getCarId()));
			renting.setTotalPrice(totalPrice);
		}
		
		rentingDao.save(renting);
		return new SuccessResult("Renting.Updated");
	}



	@Override
	public DataResult<List<ListRentingDto>> getAll() {

		var result = this.rentingDao.findAll();
		List<ListRentingDto> response = result.stream().map(rentalCar ->this.modelMapperService.forDto()
				.map(rentalCar, ListRentingDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListRentingDto>>(response);
	}



	@Override
	public DataResult<List<RentingByCarIdDto>> getRentingByCarId(int carId) throws BusinessException {
		this.carService.checkIfExistByCarId(carId);
		
		var result = this.rentingDao.getRentingByRentedCar_carId(carId);
		List<RentingByCarIdDto> response = result.stream().map(rentalCar ->this.modelMapperService.forDto()
				.map(rentalCar, RentingByCarIdDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentingByCarIdDto>>(response);
	}



	@Override
	public DataResult<RentingByIdDto> getRentingById(int rentingId) throws BusinessException {
		checkIfExistById(rentingId);
		
		var result = this.rentingDao.findByRentingId(rentingId);
		RentingByIdDto response = this.modelMapperService.forDto().map(result, RentingByIdDto.class);
		
		return new SuccessDataResult<RentingByIdDto>(response);
	}



	@Override
	public boolean checkIfCarNotInRent(int carId) throws BusinessException {
		if(this.rentingDao.getByReturnDateAndRentedCar_carId(null, carId) != null) {
			throw new BusinessException("The car in rent.");
		}
		return true;
	}

	@Override
	public boolean checkIfExistById(int rentingId) throws BusinessException {
		if(this.rentingDao.findByRentingId(rentingId) == null) {
			throw new BusinessException("Can not find rental car you wrote id");
		}
		return true;
	}
	
	private boolean checkIfCarInRent(int carId) throws BusinessException {
		if(this.rentingDao.getByReturnDateAndRentedCar_carId(null, carId) == null) {
			throw new BusinessException("The car is not in rent.");
		}
		return true;
	}
	
	private double totalPriceCalculate(Renting renting) {
		long daysBetween = Duration.between(renting.getRentDate(), renting.getReturnDate()).toDays();
		double totalPrice = daysBetween*renting.getRentedCar().getDailyPrice();
		
		if(renting.getOrderedAdditionalServices() != null) {
			for(OrderedAdditionalService orderedAdditionalService : renting.getOrderedAdditionalServices()) {
				totalPrice += daysBetween*orderedAdditionalService.getAdditionalService().getAdditionalServiceDailyPrice();
			}
		}
		if(!renting.getRentCity().equals(renting.getReturnCity())) {
			totalPrice += 750;
		}
		return totalPrice;
	}
	

	public boolean checkIfReturnDateAfterRentDate(LocalDate rentDate, LocalDate returnDate) throws BusinessException {
		if(!rentDate.isBefore(returnDate)) {
			throw new BusinessException("Can not be return date before rent date.");
		}
		return true;
	}


}
