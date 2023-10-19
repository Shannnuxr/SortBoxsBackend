package com.example.SortBoxs.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.SortBoxs.entitys.Invoice;
import com.example.SortBoxs.repository.InvoiceRepository;

import java.util.List;

@Service
public class InvoiceService {

	  @Autowired
	    private InvoiceRepository invoiceRepository;

	    public Invoice saveInvoice(Invoice invoice) {
	        return invoiceRepository.save(invoice);
	    }

//	    public Invoice getInvoice(String userId) {
//	        return invoiceRepository.findByInvoiceId(userId);
//	    }

	    public Long getNextId() {
	        List<Invoice> allEntities = invoiceRepository.findAll(Sort.by(Sort.Order.desc("id")));
	        if (allEntities.isEmpty()) {
	            return 1L; // If no records are present, start with 1.
	        } else {
	            return allEntities.get(0).getId() + 1;
	        }
	    }

	    public List<Invoice> getInvoicesByUser(String userId) {
	        return invoiceRepository.findAllByUserId(userId);
	    }

	    public void removeInvoice(Invoice invoice) {
	        invoiceRepository.delete(invoice);
	    }



	    public Invoice getInvoiceByInvoiceId(String invoiceId) {
	        return invoiceRepository.findByInvoiceId(invoiceId);
	    }

}
