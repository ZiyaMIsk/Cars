package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CorporateCustomerService;
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
		
		return new SuccessResult("CorporateCustomer.Added");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomer.setUserId(this.corporateCustomerDao.findByEmail(updateCorporateCustomerRequest.getEmail()).getUserId());
		
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer.Update");
	}

	@Override
	public Result delete(int id) throws BusinessException {

		checkIfCorporateCustomerExists(id);
		
		this.corporateCustomerDao.deleteById(id);;
		
		return new SuccessResult("CorporateCustomer.Deleted");
		
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		var result = this.corporateCustomerDao.findAll();
		List<ListCorporateCustomerDto> response = result.stream().map(corporateCustomer->this.modelMapperService.forDto()
				.map(corporateCustomer, ListCorporateCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult <List<ListCorporateCustomerDto>>(response);
	}

	@Override
	public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {
		CorporateCustomer result = checkIfCorporateCustomerExists(id);
		CorporateCustomerDto response = this.modelMapperService.forDto().map(result, CorporateCustomerDto.class);
		
		return new SuccessDataResult<CorporateCustomerDto>(response, "Success");
	}
	
	private CorporateCustomer checkIfCorporateCustomerExists(int id) throws BusinessException {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getByCorporateCustomerId(id);
		
		if(this.corporateCustomerDao.getByCorporateCustomerId(id)==null) {
			throw new BusinessException("Cannot find a corporate customer with this Id.");
		}
		return corporateCustomer;
	}
	
	
	private boolean checkIfMailExists(String mail) throws BusinessException {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.findByEmail(mail);
		
		if(corporateCustomer!=null) {
			throw new BusinessException("Such a mail exists.");
		}
		return true;
	}
	
	private boolean checkIfTaxNumberExists(String taxNmuber) throws BusinessException {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.findByTaxNumber(taxNmuber);
		
		if(corporateCustomer!=null) {
			throw new BusinessException("Such a tax number exists.");
		}
		return true;
	}
	
}

