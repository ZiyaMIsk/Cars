package com.turkcell.RentACar.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payments")
@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="payment_id")
	private int paymentId;

	@Column(name = "payment_date")
	private LocalDate paymentDate;
	

	@Column(name = "total_payment")
	private double totalPayment;
	
	@ManyToOne
	@JoinColumn(name = "renting_id")
	private Renting renting;
	
	@OneToOne
	@JoinColumn(name="invoice_id")
	private Invoice invoice;
	
	@OneToOne
	@JoinColumn(name="ordered_additional_service_id")
	private OrderedAdditionalService orderedAdditionalService;
	
	@ManyToOne
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;

}
