package com.turkcell.RentACar.business.dtos.corparateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCorporateCustomerDto {
	
	private int id;
	
	private String email;
	
	private String password;
	
	private String companyName;
	
	private String taxNumber;
	
}
