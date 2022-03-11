package com.turkcell.RentACar.business.dtos.carMaintenance;

import java.time.LocalDate;

import com.turkcell.RentACar.entities.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceDto {
	
	
	private String description;
	private LocalDate returnDate;
	private Car car;

}
