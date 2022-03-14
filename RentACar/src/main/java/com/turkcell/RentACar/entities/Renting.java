package com.turkcell.RentACar.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="rentings")
@Entity
public class Renting {
	 	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "renting_id")
	    private int rentingId;
	 	
	    @Column(name = "rent_date")	
	    private LocalDate rentDate;
	    
	    @Column(name = "return_date")
	    private LocalDate returnDate;
	    
	    @Column(name = "totalPrice")
	    private double totalPrice;
	    
	    @ManyToOne
	    @JoinColumn(name = "id")
	    private Customer customer;

	    @ManyToOne
	    @JoinColumn(name = "car_id")
	    private Car rentedCar;

	    @ManyToOne
	    @JoinColumn(name = "return_city")
	    private City returnCity ;
	    
	    @ManyToOne
	    @JoinColumn(name = "rent_city")
	    private City rentCity;
	    
	    @OneToMany(mappedBy="renting")
		private List<OrderedAdditionalService> orderedAdditionalServices;

}
