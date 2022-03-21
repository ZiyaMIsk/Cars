package com.turkcell.RentACar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
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
//@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "customer_id")
@Table(name = "invidiual_customers")
@EqualsAndHashCode(callSuper = false)
public class IndividualCustomer extends Customer{
	
	/*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "individualCustomerId")
    private int individualCustomerId;*/
	
	 @Column(name = "first_name")
	private String firstName;
	
	 @Column(name = "last_name")
	private String lastName;
	
	 @Column(name = "identity_number")
	private String identityNumber;
		


}
