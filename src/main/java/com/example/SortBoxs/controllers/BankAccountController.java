package com.example.SortBoxs.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SortBoxs.dtos.BankDetailsResponse;
import com.example.SortBoxs.entitys.BankDetails;
import com.example.SortBoxs.repository.BankRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class BankAccountController {

	@Autowired
	private BankRepository bankRepository;
	
	@PostMapping("/addAccount")
	public ResponseEntity<BankDetailsResponse> addBankDetails(@RequestBody BankDetails bankDetails){
		
		ZoneId indianZone = ZoneId.of("Asia/Kolkata"); // Use the Indian time zone

	    ZonedDateTime currentIndianTime = ZonedDateTime.now(indianZone);
	    bankDetails.setCreatedAt(currentIndianTime.toLocalDateTime());
		
		
		BankDetails savedBankDetails = bankRepository.save(bankDetails);		
		
		 if (savedBankDetails != null) {
		        // Return a JSON response with a success message
		        return ResponseEntity.ok(new BankDetailsResponse("Bank details added successfully"));
		    }

		    // Return a JSON response with an error message
		    return ResponseEntity.notFound().build();
		    
	}
	@GetMapping("/getAccount")
	public ResponseEntity<List<BankDetails>> getBankDetailsByUserId(@RequestParam("userId") Long userId) {
	    // Check if the userId is not null
	    if (userId == null) {
	        return ResponseEntity.badRequest().build();
	    }

	    // Use the bankRepository to fetch the bank details for the given userId
	    List<BankDetails> bankDetailsList = bankRepository.findByUserId(userId);

	    // Check if bank details were found
	    if (bankDetailsList.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(bankDetailsList);
	}
	
	
	
}
