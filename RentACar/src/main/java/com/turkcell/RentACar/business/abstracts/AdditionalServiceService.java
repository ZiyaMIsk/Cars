package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.RentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.entities.AdditionalService;

public interface AdditionalServiceService {
	
	DataResult<List<ListAdditionalServiceDto>> listAll() throws BusinessException;
    
	Result create(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;
    
	Result update(int id, UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;
    
	Result delete(int id) throws BusinessException;
    
	DataResult<AdditionalServiceDto> getById(int additionalServiceId) throws BusinessException;
    
	public boolean checkIfExistById(int additionalServiceId) throws BusinessException;
	
	public AdditionalService getByIdForOtherService(int additionalServiceId);
	
}
