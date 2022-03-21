package com.turkcell.RentACar.business.requests.delete;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderedAdditionalServiceRequest {
		
	@Positive
	@NotNull
	private int rentingId;
	
	@Positive
	@NotNull
	private int additionalServiceId;

}
