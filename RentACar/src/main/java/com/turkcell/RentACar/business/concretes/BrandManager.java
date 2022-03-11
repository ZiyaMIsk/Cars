package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.BrandService;
import com.turkcell.RentACar.business.dtos.brand.BrandDto;
import com.turkcell.RentACar.business.dtos.brand.ListBrandDto;
import com.turkcell.RentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.RentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorDataResult;
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
		if (!checkIfBrandListEmpty(brands).isSuccess()) {
			return new ErrorDataResult<List<ListBrandDto>>(checkIfBrandListEmpty(brands).getMessage());
		}
		List<ListBrandDto> listBrandDto = brands.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListBrandDto>>(listBrandDto, "Data listed");
	}
	
	@Override
	public Result create(CreateBrandRequest createBrandRequest) {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		if (!checkBrandName(brand.getBrandName()).isSuccess()) {
			return new ErrorResult(checkBrandName(brand.getBrandName()).getMessage());
		}
		this.brandDao.save(brand);
		return new SuccessDataResult<CreateBrandRequest>(createBrandRequest, "Data added : " + brand.getBrandName());
	}
	
	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		if (!checkBrandId(updateBrandRequest.getBrandId()).isSuccess()) {
			return new ErrorResult(checkBrandId(updateBrandRequest.getBrandId()).getMessage());
		}
		if (!checkBrandName(updateBrandRequest.getBrandName()).isSuccess()) {
			return new ErrorResult(checkBrandName(updateBrandRequest.getBrandName()).getMessage());
		}
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandDao.save(brand);
		return new SuccessDataResult<UpdateBrandRequest>(updateBrandRequest,
				"Data updated to: " + brand.getBrandName());
	}
	
	@Override
    public Result delete(int brandId){
		if (!checkBrandId(brandId).isSuccess()) {
			return new ErrorResult(checkBrandId(brandId).getMessage());
		}
		String brandNameBeforeDelete = this.brandDao.findByBrandId(brandId).getBrandName();
		this.brandDao.deleteById(brandId);
		return new SuccessResult("Data deleted : " + brandNameBeforeDelete);
    }
	
	@Override
	public DataResult<BrandDto> getById(int brandId){
		if (!checkBrandId(brandId).isSuccess()) {
			return new ErrorDataResult<BrandDto>(checkBrandId(brandId).getMessage());
		}
		Brand brand = this.brandDao.getById(brandId);
		BrandDto brandDto = this.modelMapperService.forDto().map(brand, BrandDto.class);
		return new SuccessDataResult<BrandDto>(brandDto, "Data getted by id");
	}
		
	private Result checkBrandName(String brandName){
		if (this.brandDao.existsByBrandName(brandName)) {
			return new ErrorResult("This brand name is already exists: " + brandName);
		}
		if (brandName.isBlank() || brandName.isEmpty()) {
			return new ErrorResult("Brand name cannot be blank or empty!");
		}
		return new SuccessResult();
	}
	
	private Result checkBrandId(int brandId){
		if (!this.brandDao.existsById(brandId)) {
			return new ErrorResult("This brand id is undefined!");
		}
		return new SuccessResult();
	}
	
	private Result checkIfBrandListEmpty(List<Brand> brands) {
		if (brands.isEmpty()) {
			return new ErrorDataResult<List<Brand>>("There is no brand exists in the list!");
		}
		return new SuccessResult();
	}
}
