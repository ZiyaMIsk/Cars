package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.RentACar.entities.Payment;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface PaymentDao extends JpaRepository<Payment, Integer> {

	public Payment getByPaymentId(int paymentId);

	Payment getAllByRenting_RentingId(int rentingId);
	
}
