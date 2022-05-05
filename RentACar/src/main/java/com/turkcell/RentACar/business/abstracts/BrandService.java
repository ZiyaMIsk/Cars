package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.brand.BrandDto;
import com.turkcell.RentACar.business.dtos.brand.ListBrandDto;
import com.turkcell.RentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.RentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface BrandService {


    DataResult<List<ListBrandDto>> getAll();

    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    Result delete(int brandId) throws BusinessException;

    DataResult<BrandDto> getById(int brandId) throws BusinessException;

    void isExistsByBrandId(int brandId) throws BusinessException;
}
