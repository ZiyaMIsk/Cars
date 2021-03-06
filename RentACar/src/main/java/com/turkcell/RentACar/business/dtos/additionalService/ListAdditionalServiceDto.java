package com.turkcell.RentACar.business.dtos.additionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAdditionalServiceDto {
	
	private int additionalServiceId;
	
	private String additionalServiceName;
	
	private double additionalServiceDailyPrice;

}
