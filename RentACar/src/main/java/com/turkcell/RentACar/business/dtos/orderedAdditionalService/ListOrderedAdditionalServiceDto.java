package com.turkcell.RentACar.business.dtos.orderedAdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderedAdditionalServiceDto {

	private int orderedAdditionalServiceId;
	
	private int rentingId;
	
	private int additionalServiceId;
	
	private String additionalServiceName;

	public int additionalServiceDailyPrice;

	public int orderedAdditionalServiceAmount;
}
