package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.color.ColorDto;
import com.turkcell.RentACar.business.dtos.color.ListColorDto;
import com.turkcell.RentACar.business.requests.create.CreateColorRequest;
import com.turkcell.RentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface ColorService {
	
	DataResult<List<ListColorDto>> listAll();
	Result create(CreateColorRequest createColorRequest);
	Result delete(int colorId);
	Result update(UpdateColorRequest updateColorRequest);
	DataResult<ColorDto> getById(int colorId);

}
