package com.turkcell.RentACar.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cars")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "Lazy"})
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_id")
	private int carId;
	
	@Column(name="car_name")
	private String carName;
	
	@Column(name="daily_price")
	private double dailyPrice;
	
	@Column(name="model_Year")
	private int modelYear;
	
	@Column(name="description")
	private String description;
	
	@Column( name = "kilometer_value")
	private double kilometerValue;
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name="color_id")
	private Color color;	
	
	
	@OneToMany(mappedBy = "carMaintenanceCar", fetch = FetchType.LAZY)
	private List<CarMaintenance> carMaintenanceList;
	
	@OneToMany(mappedBy = "rentedCar", fetch = FetchType.LAZY)
    private List<Renting> rentings;


    //private boolean isActive = true;
	
	@OneToMany(mappedBy = "car")
	private List<CarCrush> carAccidents;

	
}
