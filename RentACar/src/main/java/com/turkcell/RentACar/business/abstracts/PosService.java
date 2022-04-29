package com.turkcell.RentACar.business.abstracts;

import com.turkcell.RentACar.business.requests.create.CreateCreditCardRequest;

public interface PosService {

	public boolean payment(CreateCreditCardRequest createCreditCardRequest, double paymentAmount);	

}
