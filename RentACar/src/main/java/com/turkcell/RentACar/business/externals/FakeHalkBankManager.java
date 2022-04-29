package com.turkcell.RentACar.business.externals;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class FakeHalkBankManager {

	public boolean doPayment(int cardCvvNumber, String cardOwnerName, LocalDate validationDate,  String cardNumber) {
		
		return true;
	}

}
