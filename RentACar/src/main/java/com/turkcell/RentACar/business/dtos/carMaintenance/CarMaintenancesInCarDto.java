package com.turkcell.RentACar.business.dtos.carMaintenance;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.RentACar.business.dtos.color.ListColorDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenancesInCarDto {
	
	private int carMaintenanceId;
	private String description;
	private LocalDate returnDate;
	private String brandName;
	private List<ListColorDto> colors;

}
