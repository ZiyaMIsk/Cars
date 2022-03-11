package com.turkcell.RentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.dtos.renting.ListRentingDto;
import com.turkcell.RentACar.business.requests.create.CreateRentingRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteRentingRequest;
import com.turkcell.RentACar.business.requests.update.UpdateRentingRequest;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.CarDao;
import com.turkcell.RentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.RentACar.dataAccess.abstracts.RentingDao;
import com.turkcell.RentACar.entities.CarMaintenance;
import com.turkcell.RentACar.entities.Renting;

@Service
public class RentingManager implements RentingService{
	private RentingDao rentingDao;
	private ModelMapperService modelMapperService;
	private CarDao carDao;
	private CarMaintenanceDao carMaintenanceDao;
	
	
	
	@Autowired
	public RentingManager(RentingDao rentingDao, ModelMapperService modelMapperService) {
		this.rentingDao = rentingDao;
		this.modelMapperService = modelMapperService;
	
	}
	
	
	@Override
	public Result create(CreateRentingRequest createRentingRequest) {
		Renting renting = this.modelMapperService.forRequest().map(createRentingRequest, Renting.class);   

		int rentedCarPrice;
		rentedCarPrice= 
		if (checkIfCityChanged(renting.getRentCity().getCityName(), renting.getReturnCity().getCityName())) {
		
		}
		
		if (!checkRentingId(renting.getId()).isSuccess()) {
			return new ErrorResult(checkRentingId(renting.getId()).getMessage());
		}
		
		if (!checkCarAlreadyRented(createRentingRequest.getCarId()).isSuccess()) {
			
			return new ErrorResult(checkCarAlreadyRented(createRentingRequest.getCarId()).getMessage());
		}
		
		if (!checkCarNotInMaintenance(createRentingRequest.getCarId()).isSuccess()) {
			
			return new ErrorResult(checkCarNotInMaintenance(createRentingRequest.getCarId()).getMessage());
		}
		
		if (!checkCarId(renting.getRentingCar().getCarId()).isSuccess()) {
			
			return new ErrorResult(checkCarId(renting.getRentingCar().getCarId()).getMessage());
		}
		
		this.rentingDao.save(renting);         
		return new SuccessResult("eklendi.");    


	}
	
	private boolean checkIfCityChanged(String rentedCity, String returnCity) {
		if (rentedCity.equals(returnCity)) {
		
			return true;
		}
		return false;
	}
	
	private double rentedCarPrice(Renting rentings){
		return 0;
		
	}

	private Result checkRentingId(int rentingId) {
		if (!this.rentingDao.existsById(rentingId)) {
			return new ErrorResult("This renting is undefined!");
		}
		return new SuccessResult();
	}
	
	private Result checkCarNotInMaintenance(int carId) {
		List<CarMaintenance> carMaintenances = this.carMaintenanceDao.getCarMaintenanceByCarMaintenanceCar_CarId(carId);
		if (!carMaintenances.isEmpty()) {
			for (CarMaintenance carMaintenance : carMaintenances) {
				if (carMaintenance.getReturnDate() == null) {
					return new ErrorResult("Car is in maintenance!");
				}
			}
		}
		return new SuccessResult();
	}

	private Result checkCarAlreadyRented(int carId) {
		List<Renting> rentings = this.rentingDao.getRentingByRentedCar_CarId(carId);
		if (!rentings.isEmpty()) {
			for (Renting renting : rentings) {
				if (renting.getReturnDate() == null) {
					return new ErrorResult("Car is already in rented!");
				}
			}
		}
		return new SuccessResult();
	}
	private Result checkCarId(int carId) {
		if (!this.carDao.existsById(carId)) {
			return new ErrorResult("Car is not found!");
		}
		return new SuccessResult();
	}


	@Override
	public Result delete(DeleteRentingRequest deleteRentingRequest) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Result update(UpdateRentingRequest updateRentingRequest) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DataResult<List<ListRentingDto>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DataResult<ListRentingDto> getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}


}
