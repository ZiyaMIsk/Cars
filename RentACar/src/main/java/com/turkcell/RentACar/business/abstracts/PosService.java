package com.turkcell.RentACar.business.abstracts;

import com.turkcell.RentACar.business.requests.create.CreatePaymentRequest;

public interface PosService {
	
	public boolean payment(CreatePaymentRequest createPaymentRequest);
	
	

}
