package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.city.ListCityDto;
import com.turkcell.RentACar.core.utilites.results.DataResult;

public interface CityService {
	
	public DataResult<List<ListCityDto>> getAll();

}
