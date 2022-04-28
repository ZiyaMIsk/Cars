package com.turkcell.RentACar.business.adapter;

import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.PosService;
import com.turkcell.RentACar.business.externals.FakeIsBankManager;
import com.turkcell.RentACar.business.requests.create.CreatePaymentRequest;

@Service
public class FakeIsbankPosServiceAdapter implements PosService{

	@Override
	public boolean payment(CreatePaymentRequest createPaymentRequest) {
		
		FakeIsBankManager fakeIsBankManager=new FakeIsBankManager();
		
		fakeIsBankManager.makePayment( createPaymentRequest.getCreateCreditCardRequest().getCardOwnerName(), createPaymentRequest.getCreateCreditCardRequest().getCardNumber(),createPaymentRequest.getCreateCreditCardRequest().getCardCvvNumber());
		
		return true;
		
	}

	

}

