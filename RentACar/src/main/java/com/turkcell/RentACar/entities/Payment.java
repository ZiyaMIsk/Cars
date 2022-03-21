package com.turkcell.RentACar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@OneToOne
	@JoinColumn(name="invoice_id")
	private Invoice invoice;
	
	@OneToOne
	@JoinColumn(name="ordered_additional_service_id")
	private OrderedAdditionalService orderedAdditionalService;
	
	@Column(name="card_owner_name")
	private String cardOwnerName;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="card_cvv_number")
	private int cardCvvNumber;

}
