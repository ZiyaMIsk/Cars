package com.turkcell.RentACar.business.requests.update;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
	
	@Email
	private String email;
	
	@Size(min=4,max=9)
	private String password;

}
