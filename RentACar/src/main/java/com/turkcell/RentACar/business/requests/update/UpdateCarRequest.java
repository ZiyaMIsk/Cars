package com.turkcell.RentACar.business.requests.update;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	@NotNull
	private int carId;
	
	@NotNull
	private String carName;
	
	@NotNull
	private double dailyPrice;
	
	@NotNull
	private String description;
	
	@NotNull
	private double kilometerValue;
	
}
