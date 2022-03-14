package com.turkcell.RentACar.business.dtos.renting;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListRentingDto {

	private int rentingId;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private int carId;
	private int customerId;
	private List<String> additionalServiceName;
	private double totalDailyPrice;
	
}
