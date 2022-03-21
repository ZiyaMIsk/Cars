package com.turkcell.RentACar.business.requests.create;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.RentACar.entities.City;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentingRequest {
	
	@NotNull
	@FutureOrPresent
	private LocalDate rentDate;
	
	@NotNull
	@FutureOrPresent
	private LocalDate returnDate;
	
	@NotNull
	@Positive
	private  int customerId;
	
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	private City returnCity;
	
	@NotNull
	private City rentCity;
	

}
