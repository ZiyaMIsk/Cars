package com.turkcell.RentACar.business.requests.delete;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class DeleteCarCrushRequest {

	@NotNull
	@Positive
	private int carCrushId;
	
}
