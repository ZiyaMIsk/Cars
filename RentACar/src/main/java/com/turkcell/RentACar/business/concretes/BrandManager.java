package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.BrandService;
import com.turkcell.RentACar.business.abstracts.CarService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.brand.BrandDto;
import com.turkcell.RentACar.business.dtos.brand.ListBrandDto;
import com.turkcell.RentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.RentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.RentACar.entities.Brand;

@Service
public class BrandManager implements BrandService {
    private final ModelMapperService modelMapperService;
    private final BrandDao brandDao;
    private CarService carService;

    public BrandManager(ModelMapperService modelMapperService, BrandDao brandDao
    ,CarService carService) {
        this.modelMapperService = modelMapperService;
        this.brandDao = brandDao;
        this.carService=carService;
    }

    @Override
    public DataResult<List<ListBrandDto>> getAll() {

        List<Brand> brands = this.brandDao.findAll();

        List<ListBrandDto> result = brands.stream()
                .map(brand -> this.modelMapperService.forDto().map(brand,ListBrandDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,Messages.BRANDLISTED);
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {

        isExistsByBrandName(createBrandRequest.getBrandName());

        Brand brand=this.modelMapperService.forRequest().map(createBrandRequest,Brand.class);
        brand.setBrandId(0);
        this.brandDao.save(brand);

        return new SuccessResult(Messages.BRANDADDED);
    }

    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {

        isExistsByBrandId(updateBrandRequest.getBrandId());
        isExistsByBrandName(updateBrandRequest.getBrandName());

        Brand brand=this.modelMapperService.forRequest().map(updateBrandRequest,Brand.class);
        this.brandDao.save(brand);

        return new SuccessResult(Messages.BRANDUPDATED);
    }

    @Override
    public Result delete(int brandId) throws BusinessException {

        isExistsByBrandId(brandId);
        this.carService.isExistsBrandByBrandId(brandId);
        this.brandDao.deleteById(brandId);

        return new SuccessResult(Messages.BRANDDELETED);
    }

    @Override
    public DataResult<BrandDto> getById(int brandId) throws BusinessException {

        isExistsByBrandId(brandId);

        Brand brand = this.brandDao.getById(brandId);
        BrandDto getBrandDto = this.modelMapperService.forDto().map(brand,BrandDto.class);

        return new SuccessDataResult<>(getBrandDto,Messages.BRANDFOUND);
    }

    private void isExistsByBrandName(String brandName) throws BusinessException {
        if(this.brandDao.existsByBrandName(brandName)){
            throw new BusinessException(Messages.BRANDNAMENOTFOUND);
        }
    }

    @Override
    public void isExistsByBrandId(int brandId) throws BusinessException{
        if(!this.brandDao.existsByBrandId(brandId)){
            throw new BusinessException(Messages.BRANDNOTFOUND);
        }
    }

}
