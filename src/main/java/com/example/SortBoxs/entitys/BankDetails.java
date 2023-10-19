package com.example.SortBoxs.entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Account")
@Data
public class BankDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	// @Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String beneficiaryName;
	@Column(nullable = false)
	private String beneficiaryAccountNumber;
	@Column(nullable = false)
	private String bankName;
	@Column(nullable = false)
	private String bankType;
	@Column(nullable = false)
	private String ifscNumber;
	@Column(nullable = false)
	private String pan;
	@Column(nullable = false)
	private String lutNumber;
	@Column(nullable = false)
	private String gstNumber;
	@Column(nullable = false)
	private String cin;
	@Column(nullable = false)
	private String state;
	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String zipCode;

	@Column(nullable = false)
	private String createdBy;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	

	
	private Date updatedAt;

	
	

	@Column
	private String addressLine1;
	@Column
	private String addressLine2;
	@Column
	private String updatedBy;

//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getBeneficiaryName() {
//		return beneficiaryName;
//	}
//	public void setBeneficiaryName(String beneficiaryName) {
//		this.beneficiaryName = beneficiaryName;
//	}
//	public String getBeneficiaryAccountNumber() {
//		return beneficiaryAccountNumber;
//	}
//	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
//		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
//	}
//	public String getBankName() {
//		return bankName;
//	}
//	public void setBankName(String bankName) {
//		this.bankName = bankName;
//	}
//	public String getBankType() {
//		return bankType;
//	}
//	public void setBankType(String bankType) {
//		this.bankType = bankType;
//	}
//	public String getIfscNumber() {
//		return ifscNumber;
//	}
//	public void setIfscNumber(String ifscNumber) {
//		this.ifscNumber = ifscNumber;
//	}
//	public String getPan() {
//		return pan;
//	}
//	public void setPan(String pan) {
//		this.pan = pan;
//	}
//	public String getLutNumber() {
//		return lutNumber;
//	}
//	public void setLutNumber(String lutNumber) {
//		this.lutNumber = lutNumber;
//	}
//	public String getGstNumber() {
//		return gstNumber;
//	}
//	public void setGstNumber(String gstNumber) {
//		this.gstNumber = gstNumber;
//	}
//	public String getCin() {
//		return cin;
//	}
//	public void setCin(String cin) {
//		this.cin = cin;
//	}
//	public String getState() {
//		return state;
//	}
//	public void setState(String state) {
//		this.state = state;
//	}
//	public String getCity() {
//		return city;
//	}
//	public void setCity(String city) {
//		this.city = city;
//	}
//	public String getZipCode() {
//		return zipCode;
//	}
//	public void setZipCode(String zipCode) {
//		this.zipCode = zipCode;
//	}
//	public String getAddressLine1() {
//		return addressLine1;
//	}
//	public void setAddressLine1(String addressLine1) {
//		this.addressLine1 = addressLine1;
//	}
//	public String getAddressLine2() {
//		return addressLine2;
//	}
//	public void setAddressLine2(String addressLine2) {
//		this.addressLine2 = addressLine2;
//	}
//	public Long getUserId() {
//		return userId;
//	}
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//    
//    
//
//}
}