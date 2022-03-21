package com.turkcell.RentACar.business.requests.create;

import java.util.List;

import com.turkcell.RentACar.business.dtos.color.ColorIdDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	private String carName;
	private double dailyPrice;
	private int modelYear;
	private String description;
	private double kilometerValue;
	private int brandId;
	private List<ColorIdDto> colorIds;
}
