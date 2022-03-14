package com.turkcell.RentACar.business.requests.update;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {
	

	@NotNull
	@Size(min=3)
	private String description;
	
	@NotNull
	@FutureOrPresent
	private LocalDate returnDate;
	
	@NotNull
	private int carId;

}
