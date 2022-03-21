package com.turkcell.RentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.Renting;

@Repository
public interface RentingDao extends JpaRepository<Renting, Integer>{
	

	
	Renting findByRentingId(int rentingId);
	
	List<Renting> getRentingByRentedCar_carId(int carId);
	
	Renting getByReturnDateAndRentedCar_carId(LocalDate localDate, int carId);
	
}
