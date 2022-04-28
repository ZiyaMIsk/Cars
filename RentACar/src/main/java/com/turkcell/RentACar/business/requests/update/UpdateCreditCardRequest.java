package com.turkcell.RentACar.business.requests.update;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {
	
	@NotNull
	@Positive
	private int creditCardId;
	
	@NotNull
	private String cardOwnerName;
	
	@NotNull
	@Positive
	private String cardNumber;
	
	@NotNull
	@Positive
	private int cardCvvNumber;
	
	@NotNull
	@Positive
	private LocalDate validationDate;

}
