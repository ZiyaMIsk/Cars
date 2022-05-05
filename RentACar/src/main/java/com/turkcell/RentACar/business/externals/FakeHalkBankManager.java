package com.turkcell.RentACar.business.externals;

import org.springframework.stereotype.Service;

@Service
public class FakeHalkBankManager {


	public boolean doPayment(int cardCvvNumber, String cardOwnerName, int expirationMonth, int expirationYear,
			String cardNumber) {
		return true;
		
	}

}
