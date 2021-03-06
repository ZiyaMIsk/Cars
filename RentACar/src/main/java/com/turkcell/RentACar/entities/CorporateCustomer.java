	package com.turkcell.RentACar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
/*import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "customer_id")
@Table(name = "corporate_customers")
@EqualsAndHashCode(callSuper = false)
public class CorporateCustomer extends Customer{
	
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "tax_number", unique = true)
	private String taxNumber;
	
	

}
