package com.turkcell.RentACar.business.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvidiualCustomerRequest {
	
	private String firstName;
	private String lastName;
	private String identityNumber;
	

}
