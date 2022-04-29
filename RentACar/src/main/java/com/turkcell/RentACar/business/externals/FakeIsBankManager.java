package com.turkcell.RentACar.business.externals;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service	
public class FakeIsBankManager {

	public boolean makePayment(String cardOwnerName, String cardNumber, int cardCvvNumber, LocalDate validationDate) {

		return true;
	}


}
