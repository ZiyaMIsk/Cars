package com.turkcell.RentACar.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "customer_id", referencedColumnName = "user_id")
@Table(name = "customers")
@EqualsAndHashCode(callSuper = false)
public class Customer extends User{
		
	
		
		@OneToMany(mappedBy = "customerRenting")
	    @JsonIgnore
	    private List<Renting> rentings;
	
		@OneToMany(mappedBy = "customer")
		private List<Invoice> invoices;
		
}
