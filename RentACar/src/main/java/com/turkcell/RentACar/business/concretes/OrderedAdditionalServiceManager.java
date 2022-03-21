package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.RentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.ListOrderedAdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByIdDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByRentingIdDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByIdDto;
import com.turkcell.RentACar.business.requests.create.CreateOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.RentACar.entities.AdditionalService;
import com.turkcell.RentACar.entities.OrderedAdditionalService;
import com.turkcell.RentACar.entities.Renting;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {
	
	AdditionalServiceService additionalServiceService;
	OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	ModelMapperService modelMapperService;
	RentingService rentingService;
	

	@Autowired
	public OrderedAdditionalServiceManager(ModelMapperService modelMapperService,OrderedAdditionalServiceDao orderedAdditionalServiceDao, AdditionalServiceService additionalServiceService, RentingService rentingService) 
	{
		
		this.modelMapperService = modelMapperService;
		
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		
		this.additionalServiceService = additionalServiceService;
		
		this.rentingService = rentingService;
	}
	
	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		var result = this.orderedAdditionalServiceDao.findAll();
		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService ->this.modelMapperService.forDto()
				.map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}

	@Override
	public DataResult<OrderedAdditionalServiceByIdDto> getById(int orderedAdditionalServiceId) throws BusinessException {
		checkIfExist(orderedAdditionalServiceId);
		
		var result = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(orderedAdditionalServiceId);
		OrderedAdditionalServiceByIdDto response = this.modelMapperService.forDto().map(result, OrderedAdditionalServiceByIdDto.class);
		
		return new SuccessDataResult<OrderedAdditionalServiceByIdDto>(response);
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		orderedAdditionalService.setOrderedAdditionalServiceId(0);
		
		this.rentingService.checkIfExistById(createOrderedAdditionalServiceRequest.getRentingId());
		this.additionalServiceService.checkIfExistById(createOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfExistAdditionalServiceInCarInRent(createOrderedAdditionalServiceRequest.getRentingId(), createOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessResult("OrderedAdditionalService.Added");
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfOrderedAdditionalServiceExists(id);

		this.orderedAdditionalServiceDao.deleteById(id);

		return new SuccessResult(" OrderedAdditionalService.Deleted");
		
	}

	@Override
	public Result update(int id, UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
	
		checkIfOrderedAdditionalServiceExists(id);
		
		checkIfRentingExists(updateOrderedAdditionalServiceRequest.getRentingId());
		
		checkIfAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		OrderedAdditionalService orderedadditionalService = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(id);
		updateOperation(orderedadditionalService,updateOrderedAdditionalServiceRequest);
		
		this.rentingService.checkIfExistById(updateOrderedAdditionalServiceRequest.getRentingId());
		
		this.additionalServiceService.checkIfExistById(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		
		var result = this.orderedAdditionalServiceDao.getByRenting_RentingIdAndAdditionalService_AdditionalServiceId(updateOrderedAdditionalServiceRequest.getRentingId(),
				updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		OrderedAdditionalService response = this.modelMapperService.forRequest().map(result, OrderedAdditionalService.class);
		response.setAdditionalService(this.additionalServiceService.getByIdForOtherService(updateOrderedAdditionalServiceRequest.getNewAdditionalServiceId()));
		
		return new SuccessResult("OrderedAdditionalService.Updated");
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceByRentingIdDto>> getAllByRentingId(int rentingId) throws BusinessException {
		this.rentingService.checkIfExistById(rentingId);
		
		var result = this.orderedAdditionalServiceDao.getByRenting_RentingId(rentingId);
		List<OrderedAdditionalServiceByRentingIdDto> response = result.stream().map(orderedAdditionalService ->this.modelMapperService.forDto()
				.map(orderedAdditionalService, OrderedAdditionalServiceByRentingIdDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<OrderedAdditionalServiceByRentingIdDto>>(response);
	}
	
	
	
	private boolean checkIfExistAdditionalServiceInCarInRent(int rentalCarId, int additionalServiceId) throws BusinessException {
		var result = this.orderedAdditionalServiceDao.getByRenting_RentingIdAndAdditionalService_AdditionalServiceId(rentalCarId, additionalServiceId);
		if(result != null) {
			throw new BusinessException("The additional service has been added to that car before.");
		}
		return true;
	}
	
	private Renting checkIfRentingExists(int id) throws BusinessException{
		
		RentingByIdDto rentingByIdDto = this.rentingService.getRentingById(id).getData();
		
		if (rentingByIdDto == null) {
			throw new BusinessException("Cannot find a rented with this Id.");
		}
		Renting rentalCar = this.modelMapperService.forDto().map(rentingByIdDto, Renting.class);
		return rentalCar;
	}

	private AdditionalService checkIfAdditionalServiceExists(int id) throws BusinessException{
		
		AdditionalServiceDto getAdditionalServiceByIdDto = this.additionalServiceService.getById(id).getData();
		
		if (getAdditionalServiceByIdDto == null) {
			throw new BusinessException("Cannot find an additional service with this Id.");
		}
		AdditionalService additionalService = this.modelMapperService.forDto().map(getAdditionalServiceByIdDto, AdditionalService.class);
		return additionalService;
	}
	
	private boolean checkIfOrderedAdditionalServiceExists(int id) throws BusinessException{
		
		if(this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(id)!=null) {
			return true;
		}
		throw new BusinessException("Cannot find an ordered additional service with this Id.");
	}
	
	private void updateOperation(OrderedAdditionalService orderedAdditionalService, UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException{
		
		AdditionalService additionalService = checkIfAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		orderedAdditionalService.setAdditionalService(additionalService);
		
		Renting renting = checkIfRentingExists(updateOrderedAdditionalServiceRequest.getRentingId());
		orderedAdditionalService.setRenting(renting);

		
	}
	private boolean checkIfExist(int orderedAdditionalServiceId) throws BusinessException {
		var result = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(orderedAdditionalServiceId);
		if(result == null) {
			throw new BusinessException("Can not find ordered additional service id you wrote.");
		}
		return true;
	}

	
	
}
