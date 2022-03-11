package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.renting.ListRentingDto;
import com.turkcell.RentACar.business.requests.create.CreateRentingRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteRentingRequest;
import com.turkcell.RentACar.business.requests.update.UpdateRentingRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface RentingService {
	
	Result create(CreateRentingRequest createRentingRequest);
	
	Result delete(DeleteRentingRequest deleteRentingRequest);

	Result update(UpdateRentingRequest updateRentingRequest);

	DataResult<List<ListRentingDto>> getAll();

	DataResult<ListRentingDto> getById(int id);
	

}
