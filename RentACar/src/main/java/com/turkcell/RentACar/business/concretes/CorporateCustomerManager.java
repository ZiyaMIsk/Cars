package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.corparateCustomer.CorporateCustomerDto;
import com.turkcell.RentACar.business.dtos.corparateCustomer.ListCorporateCustomerDto;
import com.turkcell.RentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.RentACar.entities.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	CorporateCustomerDao corporateCustomerDao;
	ModelMapperService modelMapperService;
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao,
			ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		
		checkIfTaxNumberExists(createCorporateCustomerRequest.getTaxNumber());
		checkIfMailExists(createCorporateCustomerRequest.getEmail());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(Messages.CORPORATECUSTOMERADDED);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomer.setUserId(this.corporateCustomerDao.findByEmail(updateCorporateCustomerRequest.getEmail()).getUserId());
		
		checkIfTaxNumberExists(updateCorporateCustomerRequest.getTaxNumber());
		checkIfMailExists(updateCorporateCustomerRequest.getEmail());
		
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(Messages.CORPORATECUSTOMERUPDATED);
	}

	@Override
	public Result delete(int id) throws BusinessException {

		checkIfCorporateCustomerExists(id);
		
		this.corporateCustomerDao.deleteById(id);;
		
		return new SuccessResult(Messages.CORPORATECUSTOMERDELETED);
		
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		var result = this.corporateCustomerDao.findAll();
		
		List<ListCorporateCustomerDto> response = result.stream().map(corporateCustomer->this.modelMapperService.forDto().map(corporateCustomer, ListCorporateCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult <List<ListCorporateCustomerDto>>(response, Messages.CORPORATECUSTOMERLISTED);
	}

	@Override
	public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {
		boolean result = checkIfCorporateCustomerExists(id);
		CorporateCustomerDto response = this.modelMapperService.forDto().map(result, CorporateCustomerDto.class);
		
		return new SuccessDataResult<CorporateCustomerDto>(response, Messages.CORPORATECUSTOMERFOUND);
	}
	
	private boolean checkIfCorporateCustomerExists(int id) throws BusinessException {
		
		var result = this.corporateCustomerDao.existsById(id);
		if (result) {
			return true;
		}
		throw new BusinessException(Messages.CORPORATECUSTOMERNOTFOUND);
	}
	
	
	private boolean checkIfMailExists(String mail) throws BusinessException {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.findByEmail(mail);
		
		if(corporateCustomer!=null) {
			throw new BusinessException(Messages.EMAILERROR);
		}
		return true;
	}
	
	private boolean checkIfTaxNumberExists(String taxNmuber) throws BusinessException {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.findByTaxNumber(taxNmuber);
		
		if(corporateCustomer!=null) {
			throw new BusinessException(Messages.CORPORATECUSTOMERTAXNUMBEREXISTS);
		}
		return true;
	}
	
}

