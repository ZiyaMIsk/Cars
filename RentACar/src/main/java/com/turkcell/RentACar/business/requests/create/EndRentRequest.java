package com.turkcell.RentACar.business.requests.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.turkcell.RentACar.business.constants.ValidationMessages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndRentRequest {

	@NotNull
	@Min(value = 1, message = ValidationMessages.RENT_ID_RULE)
	private int rentId;
	
	@NotNull
	@Min(value = 1, message = ValidationMessages.RENT_END_KILOMETER_RULE)
	private double endKilometer;

	
}
