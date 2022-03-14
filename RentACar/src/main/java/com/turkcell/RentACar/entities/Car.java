package com.turkcell.RentACar.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cars")
@Entity
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_id", unique = true)
	private int carId;
	
	@Column(name="car_name")
	private String carName;
	
	@Column(name="daily_price")
	private double dailyPrice;
	
	@Column(name="model_Year")
	private int modelYear;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="car_colors",
	joinColumns = @JoinColumn(name="car_id"),
	inverseJoinColumns = @JoinColumn(name="color_id"))
	private List<Color> colorList;	
	
	
	@OneToMany(mappedBy = "carMaintenanceCar", fetch = FetchType.LAZY)
	private List<CarMaintenance> carMaintenanceList;
	
	@OneToMany(mappedBy = "rentedCar", fetch = FetchType.LAZY)
    private List<Renting> rentings;

	@Column(name="is_active")
    private boolean isActive = true;
}
