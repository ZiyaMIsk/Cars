package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface OrderedAdditionalServiceService {
	DataResult<List<ListOrderedAdditionalServiceDto>> getAll();

	DataResult<GetOrderedAdditionalServiceDto> getById(int id);
	
	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws;

	Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest);

	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest);

}
