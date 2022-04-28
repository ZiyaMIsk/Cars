package com.turkcell.RentACar.business.requests.update;


import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.RentACar.entities.CreditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentRequest {

	@NotNull
	@Positive
	private int paymentId;
	
	@NotNull
	private LocalDate paymentDate;
	
	@NotNull
	@Positive
	private int rentingId;
	
	
	private CreditCard creditCard; 
}
