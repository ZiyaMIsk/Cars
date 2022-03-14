package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.RentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.RentACar.business.abstracts.RentingService;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.ListOrderedAdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByIdDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByRentingIdDto;
import com.turkcell.RentACar.business.requests.create.CreateOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.RentACar.entities.OrderedAdditionalService;

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
		this.rentingService.checkIfExistById(createOrderedAdditionalServiceRequest.getRentingId());
		this.additionalServiceService.checkIfExistById(createOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfExistAdditionalServiceInCarInRent(createOrderedAdditionalServiceRequest.getRentingId(), createOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest,
				OrderedAdditionalService.class);
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessResult("OrderedAdditionalService.Added");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException {
		
		checkIfNotExistAdditionalServiceInCarInRent(deleteOrderedAdditionalServiceRequest.getRentingId(),
				deleteOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		this.orderedAdditionalServiceDao.deleteById(this.orderedAdditionalServiceDao.getByRenting_RentingIdAndAdditionalService_AdditionalServiceId(deleteOrderedAdditionalServiceRequest.getRentingId(),
				deleteOrderedAdditionalServiceRequest.getAdditionalServiceId()).getOrderedAdditionalServiceId());
		
		return new SuccessResult("OrderedAdditionalService.Deleted");
		
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
	
		this.rentingService.checkIfExistById(updateOrderedAdditionalServiceRequest.getRentingId());
		
		this.additionalServiceService.checkIfExistById(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		checkIfNotExistAdditionalServiceInCarInRent(updateOrderedAdditionalServiceRequest.getRentingId(), updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfExistAdditionalServiceInCarInRent(updateOrderedAdditionalServiceRequest.getRentingId(), updateOrderedAdditionalServiceRequest.getNewAdditionalServiceId());
		
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
	
	private boolean checkIfNotExistAdditionalServiceInCarInRent(int rentalCarId, int additionalServiceId) throws BusinessException {
		var result = this.orderedAdditionalServiceDao.getByRenting_RentingIdAndAdditionalService_AdditionalServiceId(rentalCarId, additionalServiceId);
		if(result == null) {
			throw new BusinessException("The additional service can not find on that car.");
		}
		return true;
	}
	
	private boolean checkIfExist(int orderedAdditionalServiceId) throws BusinessException {
		var result = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(orderedAdditionalServiceId);
		if(result == null) {
			throw new BusinessException("Can not find ordered additional service id you wrote.");
		}
		return true;
	}
	
}
