package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.color.ColorDto;
import com.turkcell.RentACar.business.dtos.color.ListColorDto;
import com.turkcell.RentACar.business.requests.create.CreateColorRequest;
import com.turkcell.RentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface ColorService {
	
	DataResult<List<ListColorDto>> listAll();

	Result create(CreateColorRequest createColorRequest) throws BusinessException;
	
	Result delete(int colorId) throws BusinessException;
	
	Result update(int colorId, UpdateColorRequest updateColorRequest) throws BusinessException;
	
	DataResult<ColorDto> getById(int colorId) throws BusinessException;
	
	boolean checkIfExistByColorId(int colorId) throws BusinessException;

}
