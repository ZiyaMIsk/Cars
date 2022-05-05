package com.turkcell.RentACar.business.dtos.creditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {
	
	private String cardOwnerName;
	
	private String cardNumber;
	
	private int cardCvvNumber;
	
	private int expirationMonth;
	 
	private int expirationYear;
	
}
