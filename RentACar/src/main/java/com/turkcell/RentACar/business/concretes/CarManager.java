package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.BrandService;
import com.turkcell.RentACar.business.abstracts.CarService;
import com.turkcell.RentACar.business.abstracts.ColorService;
import com.turkcell.RentACar.business.dtos.brand.BrandDto;
import com.turkcell.RentACar.business.dtos.car.CarByIdDto;
import com.turkcell.RentACar.business.dtos.car.ListCarDto;
import com.turkcell.RentACar.business.dtos.color.ColorDto;
import com.turkcell.RentACar.business.requests.create.CreateCarRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorDataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.CarDao;
import com.turkcell.RentACar.entities.Car;

@Service
public class CarManager implements CarService {
	
	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private BrandService brandService;
	private ColorService colorService;
	
	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		this.carDao=carDao;
		this.modelMapperService=modelMapperService;
	}
	
	@Override
	public DataResult<List<ListCarDto>> listAll() {
		
		List<Car> cars = this.carDao.findAll();
		
		if (!checkIfCarListEmpty(cars).isSuccess()) {
			return new ErrorDataResult<List<ListCarDto>>(checkIfCarListEmpty(cars).getMessage());
		
		}
		
		List<ListCarDto> listCarDto = cars.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(listCarDto, "Data listed");
	
	}
	
	@Override
	public Result create(CreateCarRequest createCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		
		checkCarName(car.getCarName());
		checkIfBrandExists(car.getBrand().getBrandId());
		checkIfColorExists(car.getColor().getColorId());
		this.carDao.save(car);
		
		return new SuccessDataResult<CreateCarRequest>(createCarRequest, "Data added : " + car.getCarName());
	}
	
	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		
		checkCarId(updateCarRequest.getCarId());
		checkCarName(updateCarRequest.getCarName());
		checkIfBrandExists(car.getBrand().getBrandId());		
		checkIfColorExists(car.getColor().getColorId());
		
		
		this.carDao.save(car);
		return new SuccessDataResult<UpdateCarRequest>(updateCarRequest, "Data updated to: " + car.getCarName());
	}
	
	@Override
	public Result delete(int carId){
		if (!checkCarId(carId).isSuccess()) {
			return new ErrorResult(checkCarId(carId).getMessage());
		}
		String carNameBeforeDelete = this.carDao.findByCarId(carId).getCarName();
		this.carDao.deleteById(carId);
		return new SuccessResult("Data deleted : " + carNameBeforeDelete);
	}
		
	@Override
	public DataResult<CarByIdDto> getById(int carId) {
		if (!checkCarId(carId).isSuccess()) {
			return new ErrorDataResult<CarByIdDto>(checkCarId(carId).getMessage());
		}
		Car car = this.carDao.getById(carId);
		CarByIdDto carDto = this.modelMapperService.forDto().map(car, CarByIdDto.class);
		return new SuccessDataResult<CarByIdDto>(carDto, "Data getted by id");
	}
	
	private Result checkCarName(String carName){
		if (this.carDao.existsByCarName(carName)) {
			return new ErrorResult("This car name is already exists: " + carName);
		}
		if (carName.isBlank() || carName.isEmpty()) {
			return new ErrorResult("Car name cannot be blank or empty!");
		}
		return new SuccessResult();
	}
	
	private Result checkCarId(int carId){
		if (!this.carDao.existsById(carId)) {
			return new ErrorResult("This car id is undefined!");
		}
		return new SuccessResult();
	}
	
	@Override
	public DataResult<List<ListCarDto>> getAllSorted() {
		Sort sort = Sort.by(Sort.Direction.DESC, "carName");
		List<Car> cars = this.carDao.findAll(sort);
		List<ListCarDto> listCarDto = cars.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(listCarDto);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Car> cars = this.carDao.findAll(pageable).getContent();
		List<ListCarDto> listCarDto = cars.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(listCarDto);
	}

	@Override
	public DataResult<List<ListCarDto>> findByDailyPriceLessThanEqual(double dailyPrice) {
		List<Car> cars = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		if (cars.isEmpty()) {
			return new ErrorDataResult<List<ListCarDto>>(null, "No Results");
		}
		List<ListCarDto> listCarDto = cars.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(listCarDto);
	}
	
	private Result checkIfCarListEmpty(List<Car> cars) {
		if (cars.isEmpty()) {
			return new ErrorDataResult<List<Car>>("There is no car exists in the list!");
		}
		return new SuccessResult();
	}

	@Override
	public boolean checkIfExistByCarId(int carId) throws BusinessException {
		if(this.carDao.findByCarId(carId) == null) {
			throw new BusinessException("The car you wrote id is not exist.");
		}
		else{
			return true;
		}
	}


	private boolean checkIfBrandExists(int id) throws BusinessException {
		
		DataResult<BrandDto> result = this.brandService.getById(id);
		
		if (!result.isSuccess()) {
			throw new BusinessException("Cannot find a brand with this Id.");
		}
		return true;
	}

	private boolean checkIfColorExists(int colorId) throws BusinessException{
		
		DataResult<ColorDto> result = this.colorService.getById(colorId);
		
		if (!result.isSuccess()) {
			throw new BusinessException("Cannot find a color with this Id..");
		}
		return true;
	}
	
	@Override
	public Car getByIdForOtherServices(int carId) {
		return carDao.findByCarId(carId);
	}

	@Override
	public void toSetCarKilometerValue(int carId, long kilometerValue) {
		// TODO Auto-generated method stub
		
	}
}
