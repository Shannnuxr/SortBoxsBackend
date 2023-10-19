package com.example.SortBoxs.entitys;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class InvoiceDevelopers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String developerName;
	private double price;
	private int leaves;
	private LocalDate startDate;
	private LocalDate endDate;
	private double bonus;
	private double otherDeductions;
	private boolean saturdayWorking;

	// Establish a many-to-one relationship with Invoice
	@ManyToOne
	@JoinColumn(name = "invoice_id")
	@JsonBackReference
	private Invoice invoice;
}
