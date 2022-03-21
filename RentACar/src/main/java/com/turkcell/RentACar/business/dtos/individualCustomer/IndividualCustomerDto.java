package com.turkcell.RentACar.business.dtos.individualCustomer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerDto {
	
	private int individualCustomerId;
	
	private String firstName;
	
	private String lastName;
	
	private String identityNumber;
	
	private String email;
	
	private String password;

}
