package com.turkcell.RentACar.business.dtos.renting;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentingDto {
	  	
		private LocalDate rentDate;
	    private LocalDate returnDate;
	    private int customerId;
	    private int carId;


}
