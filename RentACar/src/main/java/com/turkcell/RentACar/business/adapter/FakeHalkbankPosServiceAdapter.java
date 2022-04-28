package com.turkcell.RentACar.business.adapter;

import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.PosService;
import com.turkcell.RentACar.business.externals.FakeHalkBankManager;
import com.turkcell.RentACar.business.requests.create.CreatePaymentRequest;

@Service
public class FakeHalkbankPosServiceAdapter implements PosService{

	@Override
	public boolean payment(CreatePaymentRequest createPaymentRequest) {
		
		FakeHalkBankManager fakeHalkBankManager=new FakeHalkBankManager();
		
		fakeHalkBankManager.doPayment(createPaymentRequest.getCreateCreditCardRequest().getCardCvvNumber(), createPaymentRequest.getCreateCreditCardRequest().getCardOwnerName(), createPaymentRequest.getCreateCreditCardRequest().getCardNumber());
	
		return true;
	}

	


	
}
