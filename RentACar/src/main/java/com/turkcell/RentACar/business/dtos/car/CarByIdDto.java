package com.turkcell.RentACar.business.dtos.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor	
public class CarByIdDto {
	
	private int carId;
	
	private String carName;
	
	private double dailyPrice;
	
	private int modelYear;
	
	private String description;
	
	private String brandName;
	
	 private String colorName;
	
	private double kilometerValue;
	
}
