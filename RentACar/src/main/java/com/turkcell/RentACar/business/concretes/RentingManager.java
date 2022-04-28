package com.turkcell.RentACar.business.concretes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.RentACar.business.abstracts.CarService;
import com.turkcell.RentACar.business.abstracts.CustomerService;
import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.dtos.car.CarByIdDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenancesInCarDto;
import com.turkcell.RentACar.business.dtos.customer.CustomerDto;
import com.turkcell.RentACar.business.dtos.renting.ListRentingDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByCarIdDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByIdDto;
import com.turkcell.RentACar.business.requests.create.CreateRentingRequest;
import com.turkcell.RentACar.business.requests.update.UpdateRentingRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.RentingDao;
import com.turkcell.RentACar.entities.Customer;
import com.turkcell.RentACar.entities.OrderedAdditionalService;
import com.turkcell.RentACar.entities.Renting;

@Service
public class RentingManager implements RentingService{
	private RentingDao rentingDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private CustomerService customerService;
	
	
	
	@Autowired
	public RentingManager(RentingDao rentingDao, @Lazy CarMaintenanceService carMaintenanceService, @Lazy CarService carService, ModelMapperService modelMapperService, CustomerService customerService) {
		
		this.rentingDao = rentingDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	
	}



	@Override
	public Result create(CreateRentingRequest createRentingRequest) throws BusinessException {
		
		checkIfCarNotInRent(createRentingRequest.getCarId());
		

		checkIfCarExists(createRentingRequest.getCarId());
		
		checkIfCustomerExists(createRentingRequest.getCustomerId());
		
		this.carService.checkIfExistByCarId(createRentingRequest.getCarId()); 
		this.carMaintenanceService.checkIfCarNotInMaintenance(createRentingRequest.getCarId());
		
		Renting renting = this.modelMapperService.forRequest().map(createRentingRequest, Renting.class);
		renting.setRentingId(0);
		
		checkIfCarMaintenanceExists(renting);
		
		renting.setRentingId(0);
		
		renting.setTotalPrice(totalPriceCalculator(renting));
		
		renting.setCustomerRenting(customerCorrection(createRentingRequest.getCustomerId()));
		
		renting.setStartingKilometer(this.carService.getById(createRentingRequest.getCarId()).getData().getKilometerValue());
		
		
		this.rentingDao.save(renting);
		return new SuccessResult("Renting.Added");
	}



	@Override
	public Result delete(int id) throws BusinessException {
		checkIfExistById(id);
		
		this.rentingDao.deleteById(id);
		return new SuccessResult("RentalCar.Deleted");
	}



	@Override
	public Result update(int id, UpdateRentingRequest updateRentingRequest) throws BusinessException {
		
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
		

		updateOperation(renting, updateRentingRequest);
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

private boolean checkIfCarMaintenanceExists(Renting renting) throws BusinessException{
		
		DataResult<List<CarMaintenancesInCarDto>> result = this.carMaintenanceService.getCarMaintenancesByCarId(renting.getRentedCar().getCarId());
		
		if (!result.isSuccess()) {
			return true;
		}
		
		for (CarMaintenancesInCarDto listCarMaintenanceDto : result.getData()) {
			if (listCarMaintenanceDto.getReturnDate() == null) {
				throw new BusinessException("The car cannot be sent for rent because it is on maintenance.");
			}
		}
		return true;
	}
	
	
	private void checkIfCustomerExists(int customerId) throws BusinessException {
		
		if(this.customerService.getByCustomerId(customerId)==null) {
			throw new BusinessException("The customer with this id does not exist..");
		}
		
	}
private boolean checkIfCarExists(int id) throws BusinessException{
		
		DataResult<CarByIdDto> result = this.carService.getById(id);
		if (!result.isSuccess()) {
			throw new BusinessException("The car with this id does not exist..");
		}
		return true;
	}

	private void updateOperation(Renting renting, UpdateRentingRequest updateRentalCarRequest) {
		
		renting.setReturnDate(updateRentalCarRequest.getReturnDate());
		renting.setReturnKilometer(updateRentalCarRequest.getReturnKilometer());	
	}

	private Customer customerCorrection(int customerId) throws BusinessException{
		
		CustomerDto getCustomerByIdDto = this.customerService.getByCustomerId(customerId).getData();
		
		Customer customer = this.modelMapperService.forDto().map(getCustomerByIdDto, Customer.class);
		
		return customer;
		
	}
	
	private double totalPriceCalculator(Renting renting){
		
		CarByIdDto car = this.carService.getById(renting.getRentedCar().getCarId()).getData();
		
		long dateBetween = ChronoUnit.DAYS.between(renting.getRentDate(), renting.getReturnDate());
		if(dateBetween==0) {
			dateBetween=1;
		}
		
		double rentPrice=car.getDailyPrice();
		double totalPrice=rentPrice*dateBetween;
 
        if(renting.getRentCity().getCityId()!=renting.getReturnCity().getCityId()) {
        	totalPrice=totalPrice+750;
        }
        
    		return totalPrice;
    
	}



	@Override
	public void totalPriceCalculateAfterAddAdditionalService(int rentalCarId) {
		// TODO Auto-generated method stub
		
	}

}
