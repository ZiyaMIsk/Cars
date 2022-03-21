package com.turkcell.RentACar.business.requests.create;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CreateCarCrushRequest {

	@NotNull
	private String carCrushDescription;

	@NotNull
	@Positive
	private int carId;
	
}
