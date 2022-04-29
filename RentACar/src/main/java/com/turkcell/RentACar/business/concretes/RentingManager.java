package com.turkcell.RentACar.business.concretes;

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
import com.turkcell.RentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.car.CarByIdDto;
import com.turkcell.RentACar.business.dtos.carMaintenance.CarMaintenancesInCarDto;
import com.turkcell.RentACar.business.dtos.customer.CustomerDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.ListOrderedAdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByRentingIdDto;
import com.turkcell.RentACar.business.dtos.renting.ListRentingDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByCarIdDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByIdDto;
import com.turkcell.RentACar.business.requests.create.CreateRentingRequest;
import com.turkcell.RentACar.business.requests.create.EndRentRequest;
import com.turkcell.RentACar.business.requests.update.UpdateRentingRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.RentingDao;
import com.turkcell.RentACar.entities.Car;
import com.turkcell.RentACar.entities.Customer;
import com.turkcell.RentACar.entities.Renting;

@Service
public class RentingManager implements RentingService{
	
	private RentingDao rentingDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private CustomerService customerService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	
	
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
		checkIfReturnDateAfterRentDate(createRentingRequest.getRentDate(), createRentingRequest.getReturnDate());

		checkIfCarExists(createRentingRequest.getCarId());
		
		checkIfCustomerExists(createRentingRequest.getCustomerId());
		
		this.carService.checkIfExistByCarId(createRentingRequest.getCarId()); 
		this.carMaintenanceService.checkIfCarNotInMaintenance(createRentingRequest.getCarId());
		
		Renting renting = this.modelMapperService.forRequest().map(createRentingRequest, Renting.class);
		renting.setRentingId(0);
		
		checkIfCarMaintenanceExists(renting);
		
		renting.setRentingId(0);
		
		renting.setCustomerRenting(customerCorrection(createRentingRequest.getCustomerId()));
		
		renting.setStartingKilometer(this.carService.getById(createRentingRequest.getCarId()).getData().getKilometerValue());
		
		this.carService.updateRentingStatus(renting.getRentedCar().getCarId(), true);
		
		this.rentingDao.save(renting);
		return new SuccessResult(Messages.RENTINGADDED);
	}

	@Override
	public Result delete(int id) throws BusinessException {
		checkIfExistById(id);
		
		this.rentingDao.deleteById(id);
		return new SuccessResult(Messages.RENTINGDELETED);
	}



	@Override
	public Result update(int id, UpdateRentingRequest updateRentingRequest) throws BusinessException {
		
		this.carService.checkIfExistByCarId(updateRentingRequest.getCarId());
		
		checkIfCarInRent(updateRentingRequest.getCarId());
		
		Renting renting = this.modelMapperService.forRequest().map(updateRentingRequest, Renting.class);
		
		renting.setRentingId(this.rentingDao.getByReturnDateAndRentedCar_carId(null, updateRentingRequest.getCarId()).getRentingId());
		
		checkIfReturnDateAfterRentDate(updateRentingRequest.getRentDate(), updateRentingRequest.getReturnDate());
	
		updateOperation(renting, updateRentingRequest);
		rentingDao.save(renting);
		return new SuccessResult(Messages.RENTINGUPDATED);
	}



	@Override
	public DataResult<List<ListRentingDto>> getAll() {

		var result = this.rentingDao.findAll();
		
		List<ListRentingDto> response = result.stream().map(rentalCar ->this.modelMapperService.forDto().map(rentalCar, ListRentingDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListRentingDto>>(response, Messages.RENTINGSLISTED);
	}



	@Override
	public DataResult<List<RentingByCarIdDto>> getRentingByCarId(int carId) throws BusinessException {
		this.carService.checkIfExistByCarId(carId);
		
		var result = this.rentingDao.getRentingByRentedCar_carId(carId);
		List<RentingByCarIdDto> response = result.stream().map(rentalCar ->this.modelMapperService.forDto()
				.map(rentalCar, RentingByCarIdDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentingByCarIdDto>>(response, Messages.RENTINGFOUNDBYCARID);
	}



	@Override
	public DataResult<RentingByIdDto> getRentingById(int rentingId) throws BusinessException {
		
		checkIfExistById(rentingId);
		
		var result = this.rentingDao.findByRentingId(rentingId);
		
		RentingByIdDto response = this.modelMapperService.forDto().map(result, RentingByIdDto.class);
		
		return new SuccessDataResult<RentingByIdDto>(response, Messages.RENTINGFOUND);
	}

	@Override
	public Result endRent(EndRentRequest endRentRequest) throws BusinessException {
		
		checkIfExistById(endRentRequest.getRentId());
		
		Renting rent = this.rentingDao.getById(endRentRequest.getRentId());
		
		//checkIfRentAlreadyEnded(rent);
		checkIfReturnDateDelayed(rent);
		checkIfEndKilometerValueIsCorrect(rent.getStartingKilometer(), endRentRequest.getEndKilometer());
		
		rent.setReturnDate(LocalDate.now());
		rent.setReturnKilometer(endRentRequest.getEndKilometer());
		
		this.rentingDao.save(rent);
		
		this.carService.setCarKilometer(rent.getRentedCar().getCarId(), endRentRequest.getEndKilometer());
		this.carService.updateRentingStatus(rent.getRentedCar().getCarId(), false);
		
		return new SuccessResult(Messages.RENTINGENDED);		
	}

	@Override
	public boolean checkIfCarNotInRent(int carId) throws BusinessException {
		if(this.rentingDao.getByReturnDateAndRentedCar_carId(null, carId) != null) {
			throw new BusinessException(Messages.CARISRENTEDNOW);
		}
		return true;
	}

	@Override
	public boolean checkIfExistById(int rentingId) throws BusinessException {
		if(this.rentingDao.findByRentingId(rentingId) == null) {
			throw new BusinessException(Messages.RENTINGNOTFOUND);
		}
		return true;
	}
	
	private boolean checkIfCarInRent(int carId) throws BusinessException {
		if(this.rentingDao.getByReturnDateAndRentedCar_carId(null, carId) == null) {
			throw new BusinessException(Messages.CARNOTRENTED);
		}
		return true;
	}
	
	/*@Override
	public void checkIfRentAlreadyEnded (Renting renting) throws BusinessException {
		
		Car car = this.carService.getByIdForOtherServices(renting.getRentedCar().getCarId());
		
		if(!car.isRentingStatus()) {
			
			throw new BusinessException(Messages.RENTINGALREADYENDED);
		}
	}*/
	
	private double totalPriceCalculate(int rentingId) {
		
		double differentCityPrice = 0;
		
		if(!(this.rentingDao.findByRentingId(rentingId).getRentCity().equals(this.rentingDao.findByRentingId(rentingId).getReturnCity()))) {
			
			differentCityPrice = 750;
		}
		
		long daysBetween = (ChronoUnit.DAYS.between(this.rentingDao.findByRentingId(rentingId).getRentDate(), this.rentingDao.findByRentingId(rentingId).getReturnDate())+1);

		Car car = this.carService.getByIdForOtherServices(this.rentingDao.getById(rentingId).getRentedCar().getCarId());
		double dailyPrice = car.getDailyPrice();
		
		double totalPrice = (daysBetween*dailyPrice) + differentCityPrice;
	
		return totalPrice;
	}
	
	@Override
	public void checkIfReturnDateDelayed (Renting renting) throws BusinessException {
		
		if(LocalDate.now().isAfter(renting.getRentDate())){
			
			double extraPrice = calculateExtraDaysPrice(renting.getRentingId(), LocalDate.now());
			
			throw new BusinessException(Messages.NEWPAYMENTREQUİRED + extraPrice);
		}
	}
	
	@Override
	public double calculateExtraDaysPrice (int rentingId, LocalDate date) throws BusinessException {
		
		Renting rent = this.rentingDao.getById(rentingId);
		
		long daysBetween = (ChronoUnit.DAYS.between(rent.getReturnDate(), date));
		
		Car car = this.carService.getByIdForOtherServices(rent.getRentedCar().getCarId());
		
		double dailyPrice = car.getDailyPrice();
		
		double extraDaysPrice = daysBetween * dailyPrice;
		
		List<OrderedAdditionalServiceByRentingIdDto> orderedAdditionalServices = this.orderedAdditionalServiceService.getAllByRentingId(rentingId).getData();
		
		double extraOrderedServicesPrice = 0;
		
		for (OrderedAdditionalServiceByRentingIdDto orderedServiceListDto : orderedAdditionalServices) {
			
			extraOrderedServicesPrice += orderedServiceListDto.getAdditionalServiceDailyPrice() * orderedServiceListDto.getOrderedAdditionalServiceAmount();
		}
		
		extraOrderedServicesPrice *=daysBetween;
		
		double extraPrice = extraDaysPrice + extraOrderedServicesPrice;
		
		return extraPrice;
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
	
	@Override
	public void checkIfEndKilometerValueIsCorrect(double startKilometer, double endKilometer) throws BusinessException {
		
		if(startKilometer > endKilometer) {
			
			throw new BusinessException(Messages.ENDKILOMETERVALUENOTTRUE);
		}	
	}
}
