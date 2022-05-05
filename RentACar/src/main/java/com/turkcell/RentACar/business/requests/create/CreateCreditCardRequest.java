package com.turkcell.RentACar.business.requests.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
	
	@NotNull
	@NotBlank
	private String cardOwnerName;
	
	@NotNull
	@Positive
	@NotBlank
	private String cardNumber;
	
	@NotNull
	@Positive
	@Min(value = 100)
	private int cardCvvNumber;
	
	 @NotNull
	 private int expirationMonth;


	 @NotNull
	 private int expirationYear;


}
