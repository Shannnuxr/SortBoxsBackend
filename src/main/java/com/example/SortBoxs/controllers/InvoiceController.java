package com.example.SortBoxs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SortBoxs.entitys.Invoice;
import com.example.SortBoxs.entitys.InvoiceDevelopers;
import com.example.SortBoxs.services.InvoiceDeveloperService;
import com.example.SortBoxs.services.InvoiceService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.lang.Collections;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class InvoiceController {
	

    @Autowired
    private InvoiceService invoiceServices;

    @Autowired
    private InvoiceDeveloperService developerService;


   

    @PostMapping("/add-Invoice")
    public ResponseEntity<String> createInvoice(@RequestBody Invoice invoice) {

        invoice.calculateFinancialDetails();

        String invoiceId = generateInvoiceNumber(invoiceServices.getNextId());

        invoice.setInvoiceId(invoiceId);
        Invoice savedInvoice = invoiceServices.saveInvoice(invoice);

        List<InvoiceDevelopers> developers = invoice.getDevelopers();
        for (InvoiceDevelopers developer : developers) {
            developer.setInvoice(savedInvoice);
            developerService.saveDeveloper(developer);
        }

        if (savedInvoice != null) {
            return ResponseEntity.ok("Invoice added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the invoice.");
        }
    }
    @GetMapping("/get-Invoices/{userId}")
    public ResponseEntity<List<Invoice>> getInvoicesByUser(@PathVariable String userId) {
        List<Invoice> invoices = invoiceServices.getInvoicesByUser(userId);
        if (!invoices.isEmpty()) {
            return ResponseEntity.ok(invoices);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public String generateInvoiceNumber(Long id) {
        // Get the current year
        int year = Year.now().getValue();

        // Increment the invoice counter and format it as a 4-digit number
        Long invoiceNumber = id;
        String formattedInvoiceNumber = new DecimalFormat("0000").format(invoiceNumber);

        // Create the invoice number string
        String invoice = String.format("INV-%d-QT-%s", year, formattedInvoiceNumber);
        return invoice;
    }
    
    
    @DeleteMapping("/remove-invoice/{invoiceId}")
    public ResponseEntity<String> removeInvoice(@PathVariable String invoiceId) {
        // Check if the invoice with the given invoiceId exists
        Invoice invoice = invoiceServices.getInvoiceByInvoiceId(invoiceId);

        if (invoice != null) {
            // Delete the associated developers first
            List<InvoiceDevelopers> developers = invoice.getDevelopers();
            for (InvoiceDevelopers developer : developers) {
            	developerService.removeDeveloper(developer.getId());
            }

            // Then, delete the invoice itself
            invoiceServices.removeInvoice(invoice);

            return ResponseEntity.ok("Invoice removed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice with ID " + invoiceId + " not found.");
        }
    }
    
//    public void generatePdfFromData(List<Invoice> invoices) {
//        try {
//            PdfDocument pdfDoc = new PdfDocument(new PdfWriter("invoices.pdf"));
//            Document doc = new Document(pdfDoc);
//
//            PdfFont font = PdfFontFactory.createFont();
//            
//            for (Invoice invoice : invoices) {
//                Paragraph paragraph = new Paragraph();
//                paragraph.add(new Paragraph("Invoice ID: " + invoice.getInvoiceId()));
//                paragraph.add(new Paragraph("Bill From: " + invoice.getBillFrom()));
//                // Add more data here as needed
//
//                doc.add(paragraph);
//            }
//
//            doc.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


   }