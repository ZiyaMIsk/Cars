package com.turkcell.RentACar.business.dtos.payment;

import java.time.LocalDate;

import com.turkcell.RentACar.entities.CreditCard;
import com.turkcell.RentACar.entities.Invoice;
import com.turkcell.RentACar.entities.OrderedAdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPaymentDto {

	private int paymentId;
	private LocalDate paymentDate;
	private Invoice invoice;
	private OrderedAdditionalService orderedAdditionalService;
    private CreditCard creditCard;
}
