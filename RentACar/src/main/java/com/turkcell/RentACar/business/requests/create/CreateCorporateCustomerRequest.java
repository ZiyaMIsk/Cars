package com.turkcell.RentACar.business.requests.create;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateICorparateCustomerRequest {
	
	private String companyName;
	private String taxNumber;

}
