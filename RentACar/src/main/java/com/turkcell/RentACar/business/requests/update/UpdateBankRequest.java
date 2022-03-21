package com.turkcell.RentACar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBankRequest {

	@NotNull
	@Positive
	private int bankId;
	
	@NotNull
	@Positive
	private int bankNo;

	@NotNull
	private String bankName;
	
}
