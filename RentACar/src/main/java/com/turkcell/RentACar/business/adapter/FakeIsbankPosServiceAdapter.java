package com.turkcell.RentACar.business.adapter;

import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.PosService;
import com.turkcell.RentACar.business.externals.FakeIsBankManager;
import com.turkcell.RentACar.business.requests.create.CreateCreditCardRequest;

@Service
public class FakeIsbankPosServiceAdapter implements PosService{

	@Override
	public boolean payment(CreateCreditCardRequest createCreditCardRequest, double paymentAmount) {
		
		FakeIsBankManager fakeIsBankManager=new FakeIsBankManager();
		
		fakeIsBankManager.makePayment( createCreditCardRequest.getCardOwnerName(), createCreditCardRequest.getCardNumber(), createCreditCardRequest.getCardCvvNumber(), createCreditCardRequest.getValidationDate());
		
		return true;
		
	}

	

}

