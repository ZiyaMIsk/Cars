package com.turkcell.RentACar.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
//@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
//@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "user_id")
@Table(name = "customers")
@EqualsAndHashCode(callSuper = false)
public class Customer extends User{
		
		//@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private int id;
		
		@OneToMany(mappedBy = "customer")
	    @JsonIgnore
	    private List<Renting> rentings;
	

}
