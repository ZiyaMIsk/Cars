package com.turkcell.RentACar.business.dtos.city;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCityDto {
	
	private int cityId;
	
	private String cityName;

}
