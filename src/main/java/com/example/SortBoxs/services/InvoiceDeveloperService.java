package com.example.SortBoxs.services;

import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SortBoxs.entitys.Invoice;
import com.example.SortBoxs.entitys.InvoiceDevelopers;
import com.example.SortBoxs.repository.InvoiceDeveloperRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import io.jsonwebtoken.io.IOException;
import jakarta.websocket.server.ServerEndpoint;

@Service
public class InvoiceDeveloperService {


    @Autowired
    private InvoiceDeveloperRepository developerRepository;

    public InvoiceDevelopers saveDeveloper(InvoiceDevelopers developer) {
       return developerRepository.save(developer);
    }

    public void removeDeveloper(Long developerId) {
        developerRepository.deleteById(developerId);
    }
}
