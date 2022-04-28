package com.turkcell.RentACar.business.abstracts;

import com.turkcell.RentACar.business.dtos.customer.CustomerDto;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;

public interface CustomerService {
	
	DataResult<CustomerDto> getByCustomerId(int id) throws BusinessException;

}
