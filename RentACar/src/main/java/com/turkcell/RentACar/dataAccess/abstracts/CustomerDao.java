package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

	Customer getByUserId(int id);
	
}
