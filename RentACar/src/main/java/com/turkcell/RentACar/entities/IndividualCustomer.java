package com.turkcell.RentACar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "customer_id")
@Table(name = "invidiual_customers")
public class IndividiualCustomer extends Customer{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	 @Column(name = "first_name")
	private String firstName;
	
	 @Column(name = "last_name")
	private String lastName;
	
	 @Column(name = "identity_number")
	private String identityNumber;
		


}
