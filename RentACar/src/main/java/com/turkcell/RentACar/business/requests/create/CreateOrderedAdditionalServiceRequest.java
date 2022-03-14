package com.turkcell.RentACar.business.requests.create;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {

	@Positive
	@NotNull
	private int rentingId;
	
	@Positive
	@NotNull
	private int additionalServiceId;
}
