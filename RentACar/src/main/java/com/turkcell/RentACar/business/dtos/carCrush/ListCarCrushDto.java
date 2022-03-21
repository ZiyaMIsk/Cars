package com.turkcell.RentACar.business.dtos.carCrush;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarCrushDto {

	private int carCrushId;
	
	private String carCrushDescription;

	private int carId;

}
