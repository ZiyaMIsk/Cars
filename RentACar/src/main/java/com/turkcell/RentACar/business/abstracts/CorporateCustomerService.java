package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.corparateCustomer.ListCorporateCustomerDto;
import com.turkcell.RentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteCorporateCustomerRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface CorporateCustomerService {
	
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);
	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);
	DataResult <List<ListCorporateCustomerDto>> getAll();
}
