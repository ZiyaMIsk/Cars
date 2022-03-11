package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.brand.BrandDto;
import com.turkcell.RentACar.business.dtos.brand.ListBrandDto;
import com.turkcell.RentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.RentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface BrandService {

	DataResult<List<ListBrandDto>> listAll();
	Result create(CreateBrandRequest createBrandRequest);
	Result update(UpdateBrandRequest updateBrandRequest);
	DataResult<BrandDto> getById(int brandId);
	Result delete(int brandId);
}
