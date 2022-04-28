package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.individualCustomer.IndividualCustomerDto;
import com.turkcell.RentACar.business.dtos.individualCustomer.ListIndividualCustomerDto;
import com.turkcell.RentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteIndividualCustomerRequest;
import com.turkcell.RentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface IndividualCustomerService {
	
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;
	
	Result update(int id, UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;
	
	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);
	
	DataResult<List<ListIndividualCustomerDto>> getAll();
	
	DataResult<IndividualCustomerDto> getById(int id) throws BusinessException;
	
}
