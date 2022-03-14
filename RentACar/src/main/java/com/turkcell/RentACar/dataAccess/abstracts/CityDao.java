package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.City;

@Repository
public interface CityDao  extends JpaRepository<City, Integer>{
	City findByCityId(int cityId);
}
