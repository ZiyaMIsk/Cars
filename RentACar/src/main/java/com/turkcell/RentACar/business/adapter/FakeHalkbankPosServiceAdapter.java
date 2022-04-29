package com.turkcell.RentACar.business.adapter;

import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.PosService;
import com.turkcell.RentACar.business.externals.FakeHalkBankManager;
import com.turkcell.RentACar.business.requests.create.CreateCreditCardRequest;

@Service
public class FakeHalkbankPosServiceAdapter implements PosService{

	@Override
	public boolean payment(CreateCreditCardRequest createCreditCardRequest, double paymentAmount) {
		
		FakeHalkBankManager fakeHalkBankManager=new FakeHalkBankManager();
		
		fakeHalkBankManager.doPayment(createCreditCardRequest.getCardCvvNumber(), createCreditCardRequest.getCardOwnerName(),createCreditCardRequest.getValidationDate(), createCreditCardRequest.getCardNumber());
	
		return true;
	}

	


	
}
