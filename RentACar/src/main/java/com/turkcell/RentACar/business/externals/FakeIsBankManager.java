package com.turkcell.RentACar.business.externals;

import org.springframework.stereotype.Service;

@Service	
public class FakeIsBankManager {

	public boolean makePayment(String cardOwnerName, String cardNumber, int cardCvvNumber) {

		return true;
	}

}
