package com.turkcell.RentACar.business.dtos.corparateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateCustomerDto {
	
	private String companyName;
	
	private String taxNumber;
	
	private String email;
	
	private String password;

}
