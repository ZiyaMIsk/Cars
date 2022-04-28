package com.turkcell.RentACar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarCrushRequest {

	@NotNull
	@Positive
	private int carCrushId;
	
	@NotNull
	private String carCrushDescription;
	
	@NotNull
	@Positive
	private int carId;
}

