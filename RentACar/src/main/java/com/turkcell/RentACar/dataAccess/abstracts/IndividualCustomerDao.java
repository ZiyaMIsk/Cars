package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.turkcell.RentACar.entities.IndividualCustomer;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer>{
	
	
	
	IndividualCustomer findByEmail(String email);
	
	IndividualCustomer findByIdentityNumber(String identityNmuber);
	
}
