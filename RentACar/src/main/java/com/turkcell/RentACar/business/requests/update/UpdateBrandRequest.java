package com.turkcell.RentACar.business.requests.update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
	
	@NotNull
	@Min(value = 1)
	private int brandId;
	
	@NotNull
	private String brandName;
	
}
