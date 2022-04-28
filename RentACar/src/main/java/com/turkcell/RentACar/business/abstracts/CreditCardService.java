package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.business.dtos.creditCard.CreditCardDto;
import com.turkcell.RentACar.business.dtos.creditCard.ListCreditCardDto;
import com.turkcell.RentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCreditCardRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface CreditCardService {

	DataResult<List<ListCreditCardDto>> listAll() throws BusinessException;
    
	Result create(CreateCreditCardRequest createCreditCardRequest) throws BusinessException;
    
	Result update(int id, UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException;
    
	Result delete(int id) throws BusinessException;
    
	DataResult<CreditCardDto> getById(int creditCardId) throws BusinessException;

	DataResult<List<CreditCardDto>> getByCustomerId(int customerId) throws BusinessException;

	void save(CreateCreditCardRequest createCreditCardRequest) throws BusinessException;

	
}
