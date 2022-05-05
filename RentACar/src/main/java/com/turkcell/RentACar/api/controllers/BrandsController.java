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
import com.turkcell.RentACar.core.exceptions.BusinessException;
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

    @GetMapping("/getAll")
    public DataResult<List<ListBrandDto>> getAll()
    {
        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) throws BusinessException
    {
        return this.brandService.add(createBrandRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) throws BusinessException
    {
        return this.brandService.update(updateBrandRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int brandId) throws BusinessException
    {
        return this.brandService.delete(brandId);
    }

    @GetMapping("getById")
    public DataResult<BrandDto> getById(@RequestParam int brandId) throws BusinessException
    {
        return this.brandService.getById(brandId);
    }
}
