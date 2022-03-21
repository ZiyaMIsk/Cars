package com.turkcell.RentACar.business.abstracts;

import com.turkcell.RentACar.business.requests.create.CreatePaymentRequest;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface PaymentService {

	public Result add(CreatePaymentRequest createPaymentRequest);
	
}
