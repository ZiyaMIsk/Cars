package com.turkcell.RentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.RentACar.business.abstracts.BrandService;
import com.turkcell.RentACar.business.dtos.brand.BrandDto;
import com.turkcell.RentACar.business.dtos.brand.ListBrandDto;
import com.turkcell.RentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.RentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}
	
	@GetMapping("/listAll")
	public DataResult<List<ListBrandDto>> listAll(){
		return this.brandService.listAll();
	}
	
	@PostMapping("/create")
	public Result create(@RequestBody CreateBrandRequest createBrandRequest){
		return this.brandService.create(createBrandRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("brandId") int brandId, @RequestBody UpdateBrandRequest updateBrandRequest){
		return this.brandService.update(brandId, updateBrandRequest);
	}
	
	@DeleteMapping("/delete")
    public Result delete(@RequestParam("brandId") int brandId){
		return this.brandService.delete(brandId);

    }
	@GetMapping("/getById")
	public DataResult<BrandDto> getById(@RequestParam("brandId") @Valid int brandId){
		return this.brandService.getById(brandId);
	}	
}
