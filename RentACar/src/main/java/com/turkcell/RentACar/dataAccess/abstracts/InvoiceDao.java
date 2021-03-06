package com.turkcell.RentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer>{

	Invoice getByInvoiceId(int id);
	
	List<Invoice> getByCustomer_UserId(int id);
	
	List<Invoice> findByCreateDateBetween(LocalDate startDate, LocalDate finishDate);
	
	boolean existsByInvoiceNo(String invoiceNo);
	
	Invoice getByRenting_rentingId(int id);
	
}
