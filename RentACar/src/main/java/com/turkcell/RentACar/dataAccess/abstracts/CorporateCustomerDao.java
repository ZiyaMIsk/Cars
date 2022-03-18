package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.CorporateCustomer;

@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {
	
	CorporateCustomer getByCorporateCustomerId(int id);
	
	CorporateCustomer findByEmail(String email);
	
	CorporateCustomer findByTaxNumber(String taxNumber);
	
}