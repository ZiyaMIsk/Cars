package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.orderedAdditionalService.ListOrderedAdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByIdDto;
import com.turkcell.RentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceByRentingIdDto;
import com.turkcell.RentACar.business.requests.create.CreateOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface OrderedAdditionalServiceService {
	
	DataResult<List<ListOrderedAdditionalServiceDto>> getAll();

	DataResult<OrderedAdditionalServiceByIdDto> getById(int id) throws BusinessException;
	
	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException;

	Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException;

	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;

	DataResult <List<OrderedAdditionalServiceByRentingIdDto>> getAllByRentingId(int rentingId) throws BusinessException;
}
