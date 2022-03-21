package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.corparateCustomer.CorporateCustomerDto;
import com.turkcell.RentACar.business.dtos.corparateCustomer.ListCorporateCustomerDto;
import com.turkcell.RentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface CorporateCustomerService {
	
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;
	
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);
	
	Result delete(int id) throws BusinessException;
	
	DataResult <List<ListCorporateCustomerDto>> getAll();
	
	DataResult<CorporateCustomerDto> getById(int id) throws BusinessException;
	
}
