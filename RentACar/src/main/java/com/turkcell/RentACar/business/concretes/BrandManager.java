package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.BrandService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.brand.BrandDto;
import com.turkcell.RentACar.business.dtos.brand.ListBrandDto;
import com.turkcell.RentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.RentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.RentACar.entities.Brand;

@Service
public class BrandManager implements BrandService {
	
	private BrandDao brandDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao=brandDao;
		this.modelMapperService=modelMapperService;
	}
	
	@Override
	public DataResult<List<ListBrandDto>> listAll() {
		
		List<Brand> brands = this.brandDao.findAll();
		
		List<ListBrandDto> listBrandDto = brands.stream().map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListBrandDto>>(listBrandDto, Messages.BRANDLISTED);
	
	}
	
	@Override
	public Result create(CreateBrandRequest createBrandRequest) {
		checkBrandName(createBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
		this.brandDao.save(brand);
		
		return new SuccessDataResult<CreateBrandRequest>(createBrandRequest, Messages.BRANDADDED);
	
	}
	
	@Override
	public Result update(int id, UpdateBrandRequest updateBrandRequest) {

		checkBrandId(id);
		checkBrandName(updateBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandDao.save(brand);
		
		return new SuccessDataResult<UpdateBrandRequest>(updateBrandRequest, Messages.BRANDUPDATED);
	}
	
	@Override
    public Result delete(int brandId){

		checkBrandId(brandId);
		
		this.brandDao.deleteById(brandId);
		
		return new SuccessResult(Messages.BRANDDELETED);
    }
	
	@Override
	public DataResult<BrandDto> getById(int brandId){
		
		checkBrandId(brandId);
		
		Brand brand = this.brandDao.getById(brandId);
		BrandDto brandDto = this.modelMapperService.forDto().map(brand, BrandDto.class);
		
		return new SuccessDataResult<BrandDto>(brandDto, Messages.BRANDFOUNDBYID);
	}
		
	private Result checkBrandName(String brandName){
		
		if (this.brandDao.existsByBrandName(brandName)) {
		
			return new ErrorResult(Messages.BRANDNAMENOTFOUND);
		
		}
		
		return new SuccessResult();
	}
	
	private Result checkBrandId(int brandId){
		
		if (!this.brandDao.existsById(brandId)) {
			
			return new ErrorResult(Messages.BRANDNOTFOUND);
		
		}
		
		return new SuccessResult();
	}

	
	public boolean checkIfExistByBrandId(int brandId) throws BusinessException {
		
		if(this.brandDao.findByBrandId(brandId) == null) {
			
			throw new BusinessException(Messages.BRANDNOTFOUND);
	
		}
		
		else {
			
			return true;
		}
	}

	@Override
	public Brand getBrandById(int brandId) throws BusinessException {
		isBrandExitsById(brandId);
		return this.brandDao.getById(brandId);
	}
	
	private void isBrandExitsById(int brandId) throws BusinessException {
		if (!this.brandDao.existsById(brandId)) {
			throw new BusinessException(Messages.BRANDNOTFOUND);
		}
	}
}
