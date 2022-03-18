package com.turkcell.RentACar.business.requests.delete;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteIndividualCustomerRequest {
	
	@NotNull
	private int id;
	
	@NotNull
	@Email
	private String email;
	
}
