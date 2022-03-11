package com.turkcell.RentACar.business.requests.update;

import java.util.List;

import com.turkcell.RentACar.business.dtos.color.ColorIdDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	private int carId;
	private String carName;
	private double dailyPrice;
	private int modelYear;
	private String description;
	private int brandId;
	private List<ColorIdDto> colorIds;
}
