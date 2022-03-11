package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface CustomerService {
	DataResult<List<ListCustomerDto>> listAll();
    Result create(CreateCustomerRequest createCustomerRequest);
    Result update(UpdateCustomerRequest updateCustomerRequest);
    Result delete(DeleteCustomerRequest deleteCustomerRequest);
    DataResult<CustomerDto> getById(int customerId);

}
