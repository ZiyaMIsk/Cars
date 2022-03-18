package com.turkcell.RentACar.business.requests.update;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentingRequest {
	
	@FutureOrPresent
	private LocalDate returnDate;
	
	@NotNull
	private int carId;
	
	@NotNull
	private int returnCityId;
	
	@NotNull
	private double returnKilometer;	

}
