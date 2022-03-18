package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.RentACar.entities.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

	Customer getByCustomerId(int id);
	
}
