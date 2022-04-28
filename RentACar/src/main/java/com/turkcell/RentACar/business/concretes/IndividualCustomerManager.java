package com.turkcell.RentACar.business.concretes;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.individualCustomer.IndividualCustomerDto;
import com.turkcell.RentACar.business.dtos.individualCustomer.ListIndividualCustomerDto;
import com.turkcell.RentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.RentACar.business.requests.delete.DeleteIndividualCustomerRequest;
import com.turkcell.RentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.RentACar.entities.IndividualCustomer;

@Service
public class IndividualCustomerManager  implements IndividualCustomerService {

	IndividualCustomerDao individualCustomerDao;
	ModelMapperService modelMapperService;
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}
		
	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {

		checkIfMailExists(createIndividualCustomerRequest.getEmail());
		checkIfIdentityNumberExistsAndRegex(createIndividualCustomerRequest.getIdentityNumber());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(Messages.INDIVIDUALCUSTOMERADDED);
	}

	@Override
	public Result update(int id, UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {

		checkIfMailExists(updateIndividualCustomerRequest.getEmail());
		checkIfIdentityNumberExistsAndRegex(updateIndividualCustomerRequest.getIdentityNumber());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
		individualCustomer.setUserId(this.individualCustomerDao.findByEmail(updateIndividualCustomerRequest.getEmail()).getUserId());
		
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(Messages.INDIVIDUALCUSTOMERUPDATED);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {

		this.individualCustomerDao.delete(this.individualCustomerDao.findByEmail(deleteIndividualCustomerRequest.getEmail()));
		
		return new SuccessResult(Messages.INDIVIDUALCUSTOMERDELETED);
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		
		var result = this.individualCustomerDao.findAll();
		
		List<ListIndividualCustomerDto> response = result.stream().map(individualCustomer->this.modelMapperService.forDto().map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult <List<ListIndividualCustomerDto>>(response, Messages.INDIVIDUALCUSTOMERLISTED);
	}


	@Override
	public DataResult<IndividualCustomerDto> getById(int id) throws BusinessException {

		boolean result = checkIfIndividualCustomerExists(id);
		IndividualCustomerDto response = this.modelMapperService.forDto().map(result, IndividualCustomerDto.class);
		
		return new SuccessDataResult<IndividualCustomerDto>(response, Messages.INDIVIDUALCUSTOMERFOUND);
	}
	
	private boolean checkIfIndividualCustomerExists(int id) throws BusinessException {
		
		
		var result = this.individualCustomerDao.existsById(id);
		if (result) {
			return true;
		}
		throw new BusinessException(Messages.INDIVIDUALCUSTOMERNOTFOUND);
	}
	
	private boolean checkIfMailExists(String mail) throws BusinessException {
		
		if(this.individualCustomerDao.findByEmail(mail)!=null) {
			throw new BusinessException(Messages.EMAILERROR);
		}
		return true;
	}
	
	private boolean checkIfIdentityNumberExistsAndRegex(String ıdentityNumber) throws BusinessException {
  
		if( this.individualCustomerDao.findByIdentityNumber(ıdentityNumber)!=null) {
			
			throw new BusinessException(Messages.INDIVIDUALCUSTOMERIDENTITYNUMBEREXISTS);
		}
		return true;
	}


}
