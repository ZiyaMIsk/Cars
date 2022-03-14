package com.turkcell.RentACar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {

	@Positive
	@NotNull
	private int rentingId;
	
	@Positive
	@NotNull
	private int additionalServiceId;
	
	@Positive
	@NotNull
	private int newAdditionalServiceId;

}
