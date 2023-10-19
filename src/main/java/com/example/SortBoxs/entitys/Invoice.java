package com.example.SortBoxs.entitys;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Invoice {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String invoiceId; // If you have a separate invoice ID, you can retain it here.
	    private Long userId;
	    private String billFrom;
	    private String billTo;
	    private LocalDate invoiceDate;
	    private String project;
	    private double tds;
	    private double gst;
	    private String gstType;
	    private LocalDate contractStartDate;
	    private LocalDate contractEndDate;
	    private String salesPerson;
	    private String createdBy;

	    // New fields to hold calculated values
	    private double totalAmount;
	    private double gstAmount;
	    private double tdsAmount;
	    private double amountAfterGST;
	    private double finalAmount;

	    // Establish a one-to-many relationship with InvoiceDevelopers
	    @OneToMany(mappedBy = "invoice")
	    @JsonManagedReference
	    private List<InvoiceDevelopers> developers;

	    public void calculateFinancialDetails() {
	        updateTotalAmount();
	        gstAmount = (gst / 100) * totalAmount;
	        tdsAmount = (tds / 100) * totalAmount;
	        amountAfterGST = totalAmount + gstAmount;
	        finalAmount = amountAfterGST - tdsAmount;
	    }
	    public void updateTotalAmount() {
	        totalAmount = 0.0;
	        if (developers != null) {
	            for (InvoiceDevelopers developer : developers) {
	                totalAmount += developer.getPrice();
	            }
	        }
	    }
}