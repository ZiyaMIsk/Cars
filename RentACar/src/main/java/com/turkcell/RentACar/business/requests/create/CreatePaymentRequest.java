package com.turkcell.RentACar.business.requests.create;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {


	@NotNull
	private int invoiceId;
	
	@NotNull
	private int orderedAdditionalServiceId;
	
	@NotNull
	private CreateCreditCardRequest createCreditCardRequest;

	public boolean isRememberMe;

	@JsonIgnore
	public double paymentAmount;
	
	
}
