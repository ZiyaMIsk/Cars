package com.turkcell.RentACar.business.dtos.creditCard;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {
	
	private int creditCardId;
	
	private String cardOwnerName;
	
	private String cardNumber;
	
	private int cardCvvNumber;
	
	private LocalDate validationDate;
	

}
