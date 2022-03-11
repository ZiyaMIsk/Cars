package com.turkcell.RentACar.business.requests.create;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
	private String description; 
	private int carId;
	private LocalDate returnDate;

}
