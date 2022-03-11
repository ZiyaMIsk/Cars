package com.turkcell.RentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.RentACar.entities.Renting;

public interface RentingDao extends JpaRepository<Renting, Integer>{
	List<Renting> findAllByCustomerId(int customerId);
	Renting findByRentingId(int rentalId);
	List<Renting> getRentingByRentedCar_CarId(int carId);
}
