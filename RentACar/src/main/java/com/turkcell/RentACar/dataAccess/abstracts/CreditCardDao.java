package com.turkcell.RentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.CreditCard;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {
	
	List<CreditCard>  getCreditCardByCustomer_CustomerId(int customerId);
	
	CreditCard findByCreditCardId(int creditCarId);

}
