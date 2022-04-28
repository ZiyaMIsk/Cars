package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.RentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.RentACar.entities.AdditionalService;

@Service
public class AdditionalServiceManager  implements AdditionalServiceService {
    
	private AdditionalServiceDao additionalServiceDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
        this.additionalServiceDao = additionalServiceDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<ListAdditionalServiceDto>> listAll () throws BusinessException {
    	
    	List<AdditionalService> additionalServices = this.additionalServiceDao.findAll();
    	
    	List<ListAdditionalServiceDto> response = additionalServices.stream().map(additionalService -> this.modelMapperService.forDto().map(additionalService, ListAdditionalServiceDto.class)).collect(Collectors.toList());
            
    	return new SuccessDataResult<List<ListAdditionalServiceDto>>(response, Messages.ADDITIONALSERVICELISTED);
            
    }

    @Override
    public Result create(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException{          
        	
    	checkAdditionalServiceName(createAdditionalServiceRequest.getAdditionalServiceName());
            
        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
            
        return new SuccessResult(Messages.ADDITIONALSERVICEADDED);   
    
    }

    @Override
    public Result update(int id, UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException{
            
    	checkAdditionalServiceId(updateAdditionalServiceRequest.getAdditionalServiceId());
    	checkAdditionalServiceName(updateAdditionalServiceRequest.getAdditionalServiceName());
    	
        	
    	AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);     
    	this.additionalServiceDao.save(additionalService);
    	additionalService.setAdditionalServiceDailyPrice(updateAdditionalServiceRequest.getDailyPrice());   
    	
    	return new SuccessResult(Messages.ADDITIONALSERVICEUPDATED);
    }

    @Override
    public Result delete(int id) throws BusinessException{
            
    	checkAdditionalServiceId(id);
            
       	this.additionalServiceDao.deleteById(id);
       
       	return new SuccessResult(Messages.ADDITIONALSERVICEDELETED);
    
    }
        
      
    @Override
    public DataResult<AdditionalServiceDto> getById ( int additionalServiceId) throws BusinessException{
    	
    	checkAdditionalServiceId(additionalServiceId); 
    	
    	AdditionalService additionalService = this.additionalServiceDao.getById(additionalServiceId);
        AdditionalServiceDto additionalServiceDto = this.modelMapperService.forDto().map(additionalService,AdditionalServiceDto.class);
        
        
        return new SuccessDataResult<AdditionalServiceDto>(additionalServiceDto, Messages.ADDITIONALSERVICEFOUND);
    
    }
    
    public AdditionalService getByIdForOtherService(int additionalServiceId) {
		
    	return additionalServiceDao.getById(additionalServiceId);
	
    }

	public boolean checkIfExistById(int additionalServiceId) throws BusinessException {
		
		var result = this.additionalServiceDao.findByAdditionalServiceId(additionalServiceId);
		
		if(result == null) {
			
			throw new BusinessException(Messages.ADDITIONALSERVICENOTFOUND);
		
		}
		
		return true;
	}
	
    private Result checkAdditionalServiceId(int additionalServiceId) throws BusinessException {
       
    	if (!this.additionalServiceDao.existsById(additionalServiceId)) {
           
    		throw new BusinessException(Messages.ADDITIONALSERVICENOTFOUND);
        }
        
    	return new SuccessResult();
    
    }
    

    
    private Result checkAdditionalServiceName(String additionalServiceName) throws BusinessException {
        
    	if (this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName)){
            
    		throw new BusinessException(Messages.ADDITIONALSERVICEEXİSTS);
       
    	}
        
    	return new SuccessResult();
    }

}
