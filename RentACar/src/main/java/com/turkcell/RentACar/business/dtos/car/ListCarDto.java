package com.turkcell.RentACar.business.dtos.car;

import java.util.List;

import com.turkcell.RentACar.business.dtos.color.ListColorDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarDto {

	private int carId;
	private double dailyPrice;
	private int modelYear;
	private String description;
	private String brandName;
	private List<ListColorDto> colorList;
//	private List<CarMaintenance> carMaintenance;
//	private List<RentalCar> rentalCar;
	
	private double kilometerValue;
	
}
