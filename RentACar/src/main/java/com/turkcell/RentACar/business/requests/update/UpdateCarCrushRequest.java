package com.turkcell.RentACar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UpdateCarCrushRequest {

	@NotNull
	@Positive
	private int carCrushId;
	
	@NotNull
	private String carCrushDescription;
}
