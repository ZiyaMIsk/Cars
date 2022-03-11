package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorDataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;

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
            List<ListAdditionalServiceDto> listAdditionalServiceDto = additionalServices.stream().map(additionalService -> this.modelMapperService
                    .forDto().map(additionalService, ListAdditionalServiceDto.class)).collect(Collectors.toList());
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
        public Result update (UpdateAdditionalServiceRequest updateAdditionalServiceRequest){
            AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
            checkAdditionalServiceId(updateAdditionalServiceRequest.getAdditionalServiceId());
            this.additionalServiceDao.save(additionalService);
            return new SuccessResult("Additional Service updated.");
        }

        @Override
        public Result delete (DeleteAdditionalServiceRequest deleteAdditionalServiceRequest){
            if (!checkAdditionalServiceId(deleteAdditionalServiceRequest.getAdditionalServiceId()).isSuccess()){
                return new ErrorDataResult<AdditionalServiceDto>(checkAdditionalServiceId(deleteAdditionalServiceRequest.getAdditionalServiceId()).getMessage());
            }
            AdditionalService additionalService = this.modelMapperService.forRequest().map(deleteAdditionalServiceRequest, AdditionalService.class);
            checkAdditionalServiceId(additionalService.getId());
            this.additionalServiceDao.delete(additionalService);
            return new SuccessResult("Additional Service deleted.");
        }
        @Override
        public DataResult<AdditionalServiceDto> getById ( int additionalServiceId){
            if(!checkAdditionalServiceId(additionalServiceId).isSuccess()){
                return new ErrorDataResult<AdditionalServiceDto>(checkAdditionalServiceId(additionalServiceId).getMessage());
            }
            AdditionalService additionalService = this.additionalServiceDao.getById(additionalServiceId);
            AdditionalServiceDto additionalServiceDto = this.modelMapperService.forDto().map(additionalService,AdditionalServiceDto.class);
            return new SuccessDataResult<AdditionalServiceDto>(additionalServiceDto,"Additional Service found.");
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
