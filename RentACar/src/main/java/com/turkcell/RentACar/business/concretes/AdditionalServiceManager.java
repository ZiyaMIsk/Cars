package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.RentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.RentACar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.RentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.RentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorDataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorResult;
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
    public DataResult<List<ListAdditionalServiceDto>> listAll () {
    	
    	List<AdditionalService> additionalServices = this.additionalServiceDao.findAll();
    	
    	if (!checkAdditionalServiceListEmpty(additionalServices).isSuccess()){
    		
    		return new ErrorDataResult<List<ListAdditionalServiceDto>>(checkAdditionalServiceListEmpty(additionalServices).getMessage());
    	}
    	
    	List<ListAdditionalServiceDto> listAdditionalServiceDto = additionalServices.stream().map(additionalService -> this.modelMapperService.forDto().map(additionalService, ListAdditionalServiceDto.class)).collect(Collectors.toList());
            
    	return new SuccessDataResult<List<ListAdditionalServiceDto>>(listAdditionalServiceDto,listAdditionalServiceDto.size() + " : Additional Services found.");
            
    }

    @Override
    public Result create (CreateAdditionalServiceRequest createAdditionalServiceRequest){          
        	
    	checkAdditionalServiceName(createAdditionalServiceRequest.getAdditionalServiceName());
            
        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
            
        return new SuccessResult("Additional Service added : " + additionalService.getAdditionalServiceName());   
    
    }

    @Override
    public Result update(int id, UpdateAdditionalServiceRequest updateAdditionalServiceRequest){
            
    	checkAdditionalServiceId(updateAdditionalServiceRequest.getAdditionalServiceId());
    	checkAdditionalServiceName(updateAdditionalServiceRequest.getAdditionalServiceName());
    	
        	
    	AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);     
    	this.additionalServiceDao.save(additionalService);
    	additionalService.setAdditionalServiceDailyPrice(updateAdditionalServiceRequest.getDailyPrice());   
    	
    	return new SuccessResult("Additional Service updated.");
    }

    @Override
    public Result delete(int id){
            
    	checkAdditionalServiceId(id);
            
       	this.additionalServiceDao.deleteById(id);
       
       	return new SuccessResult("Additional Service deleted.");
    
    }
        
      
    @Override
    public DataResult<AdditionalServiceDto> getById ( int additionalServiceId){
    	
    	checkAdditionalServiceId(additionalServiceId); 
    	
    	AdditionalService additionalService = this.additionalServiceDao.getById(additionalServiceId);
        AdditionalServiceDto additionalServiceDto = this.modelMapperService.forDto().map(additionalService,AdditionalServiceDto.class);
        
        
        return new SuccessDataResult<AdditionalServiceDto>(additionalServiceDto,"Additional Service found.");
    
    }
    
    public AdditionalService getByIdForOtherService(int additionalServiceId) {
		
    	return additionalServiceDao.getById(additionalServiceId);
	
    }

	public boolean checkIfExistById(int additionalServiceId) throws BusinessException {
		
		var result = this.additionalServiceDao.findByAdditionalServiceId(additionalServiceId);
		
		if(result == null) {
			
			throw new BusinessException("Can not find Additional Service you wrote id.");
		
		}
		
		return true;
	}
    private Result checkAdditionalServiceId(int additionalServiceId) {
       
    	if (!this.additionalServiceDao.existsById(additionalServiceId)) {
           
    		return new ErrorResult("Additional Service not found.");
        }
        
    	return new SuccessResult();
    
    }
    
    private Result checkAdditionalServiceListEmpty(List<AdditionalService> additionalServices){
        
    	if (additionalServices.isEmpty()){
          
    		return new ErrorDataResult<List<AdditionalService>>("Additional Service list is empty.");
        }
       
    	return new SuccessDataResult<>();
    
    }
    
    private Result checkAdditionalServiceName(String additionalServiceName) {
        
    	if (this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName)){
            
    		return new ErrorResult("This Additional Service already exists.");
       
    	}
        
    	return new SuccessResult();
    }

}
