package com.turkcell.RentACar.business.dtos.car;

import java.util.List;

import com.turkcell.RentACar.business.dtos.color.ListColorDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarByDailyPriceDto {
	
	private int carId;
	private String brandName;
	private List<ListColorDto> colors;
	private double dailyPrice;
	private int modelYear;
	private String description;
	private long kilometerValue;
}
