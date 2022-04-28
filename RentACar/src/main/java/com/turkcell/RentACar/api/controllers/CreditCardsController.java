package com.turkcell.RentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.RentACar.business.abstracts.CreditCardService;
import com.turkcell.RentACar.business.dtos.creditCard.CreditCardDto;
import com.turkcell.RentACar.business.dtos.creditCard.ListCreditCardDto;
import com.turkcell.RentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCreditCardRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/creditCards")
public class CreditCardsController{

	private CreditCardService creditCardService;

    @Autowired
    public CreditCardsController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

	@GetMapping("/listAll")
	public DataResult<List<ListCreditCardDto>> listAll() throws BusinessException {
		 return this.creditCardService.listAll();
	}

	@PostMapping("/create")
	public Result create(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {
		return this.creditCardService.create(createCreditCardRequest);
	}

	@PutMapping("/update")
	public Result update(int id, UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException {
        return this.creditCardService.update(id, updateCreditCardRequest);

	}

	@DeleteMapping("/delete")
	public Result delete(int id) throws BusinessException {
		return this.creditCardService.delete(id);
	}

	@GetMapping("/getById")
	public DataResult<CreditCardDto> getById(int creditCardId) throws BusinessException {
	    return this.creditCardService.getById(creditCardId);
	}
	
	  @GetMapping("/getByCustomerId/{customerId}")
	public DataResult<List<CreditCardDto>> getByCustomerId(@RequestParam("customerId") int customerId) throws BusinessException {
		return this.creditCardService.getByCustomerId(customerId);
	}	

}
