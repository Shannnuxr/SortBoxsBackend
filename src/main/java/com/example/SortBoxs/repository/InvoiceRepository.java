package com.example.SortBoxs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.SortBoxs.entitys.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	Invoice findByInvoiceId(String invoiceId);

	 @Query("SELECT i FROM Invoice i WHERE i.userId = :userId")
	    List<Invoice> findAllByUserId(String userId);
	
}
