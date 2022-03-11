package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.RentACar.entities.City;

public interface CityDao  extends JpaRepository<City, Integer>{
	
}
