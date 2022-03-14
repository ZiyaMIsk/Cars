package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.RentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.entities.AdditionalService;

public interface AdditionalServiceService {
	
	DataResult<List<ListAdditionalServiceDto>> listAll();
    
	Result create(CreateAdditionalServiceRequest createAdditionalServiceRequest);
    
	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);
    
	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest);
    
	DataResult<AdditionalServiceDto> getById(int additionalServiceId);
    
	public boolean checkIfExistById(int additionalServiceId) throws BusinessException;
	
	public AdditionalService getByIdForOtherService(int additionalServiceId);
}
