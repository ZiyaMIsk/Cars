package com.turkcell.RentACar.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="credit_cards")
@Entity
public class CreditCard {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_card_id")
	private int creditCardId;
	
	@Column(name="card_owner_name")
	private String cardOwnerName;
	
	@Column(name="card_number", length = 16, unique = true)
	private String cardNumber;
	
	@Column(name="crecard_cvv_number")
	private int cardCvvNumber;
    
	@Column(name = "expiration_month")
    private int expirationMonth;

    @Column(name = "expiration_year")
    private int expirationYear;	
    
	@OneToMany(mappedBy = "creditCard")
	private List<Payment> payments;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

}
