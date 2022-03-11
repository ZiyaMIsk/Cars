package com.turkcell.RentACar.business.requests.create;

import java.time.LocalDate;

import com.turkcell.RentACar.entities.City;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentingRequest {
	
	private LocalDate rentDate;
	private LocalDate returnDate;
	private double dailyPrice;
	private  int customerId;
	private int carId;
	private City returnCity;
	private City rentCity;
	

}
