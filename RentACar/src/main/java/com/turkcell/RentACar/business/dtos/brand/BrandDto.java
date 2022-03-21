package com.turkcell.RentACar.business.dtos.brand;

import java.util.List;

import com.turkcell.RentACar.business.dtos.car.ListCarDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
	
	private int brandId;
	
	private String brandName;
	
	private List<ListCarDto> cars;
	
}
