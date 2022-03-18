package com.turkcell.RentACar.business.dtos.invoice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

	private int invoiceId;

	private long invoiceNo;

	private LocalDate createDate;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private int numberDays;

	private double rentTotalPrice;

	private int customerId;

	private int rentingId;

}
