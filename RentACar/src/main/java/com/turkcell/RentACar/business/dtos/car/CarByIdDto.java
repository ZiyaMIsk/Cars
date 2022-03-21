package com.turkcell.RentACar.business.dtos.car;

import java.util.List;

import com.turkcell.RentACar.business.dtos.color.ListColorDto;

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
	
	private List<ListColorDto> colors;
	
	private double kilometerValue;
	
}
