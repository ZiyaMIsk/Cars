package com.turkcell.RentACar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="car_accidents")
@Entity
public class CarCrush {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_crush_id")
	private int carCrushId;
	
	@Column(name="car_crush_description")
	private String carCrushDescription;
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;

}
