package com.turkcell.RentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CustomerService;
import com.turkcell.RentACar.business.dtos.customer.CustomerDto;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.dataAccess.abstracts.CustomerDao;
import com.turkcell.RentACar.entities.Customer;

@Service
public class CustomerManager implements CustomerService {

	private CustomerDao customerDao;
	private ModelMapperService modelMapperService; 
	
	@Autowired
	public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}
	@Override
	public DataResult<CustomerDto> getById(int id) throws BusinessException {
		Customer result = checkIfCustomerExists(id);
		CustomerDto response = this.modelMapperService.forDto().map(result, CustomerDto.class);

		return new SuccessDataResult<CustomerDto>(response, "Success");
	}

	private Customer checkIfCustomerExists(int id) throws BusinessException {
		Customer customer = this.customerDao.getByUserId(id);

		if (customer == null) {
			throw new BusinessException("Cannot find a customer with this Id.");
		}
		return customer;
	}
	
}
