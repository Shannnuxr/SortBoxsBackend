package com.example.SortBoxs.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private Long userId;

	    @Column(nullable = false)
	    private String organization;

	    @Column(nullable = false)
	    private String projectName;

	    @Column(nullable = false)
	    private String clientName;

	    @Column
	    private String clientEmail;
	    
	    @Column
	    private String gtsn;
	    
	    @Column
	    private String startDate;
	    
	    @Column
	    private String endDate;
	    
	    @Column
	    private String sacNumber;
	    
	    @Column
	    private String country;
	    
	    @Column
	    private String state;
	    
	    
	    @Column
	    private String city;
	    
	    @Column
	    private String zipCode;
	    
	    @Column
	    private String projectLink;
	    
	    @Column
	    private String adressLine1;
	    
	    @Column
	    private String adressline2;
	    
	    
	    @Column
	    private String clientDeclaration;
	    
	    @Column
	    private String clientDescriptionForInvoice;
	    
	    @Column
	    private String projectDescription;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}



		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getOrganization() {
			return organization;
		}

		public void setOrganization(String organization) {
			this.organization = organization;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getClientName() {
			return clientName;
		}

		public void setClientName(String clientName) {
			this.clientName = clientName;
		}

		public String getClientEmail() {
			return clientEmail;
		}

		public void setClientEmail(String clientEmail) {
			this.clientEmail = clientEmail;
		}

		public String getGtsn() {
			return gtsn;
		}

		public void setGtsn(String gtsn) {
			this.gtsn = gtsn;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getSacNumber() {
			return sacNumber;
		}

		public void setSacNumber(String sacNumber) {
			this.sacNumber = sacNumber;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getZipCode() {
			return zipCode;
		}

		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}

		public String getProjectLink() {
			return projectLink;
		}

		public void setProjectLink(String projectLink) {
			this.projectLink = projectLink;
		}

		public String getAdressLine1() {
			return adressLine1;
		}

		public void setAdressLine1(String adressLine1) {
			this.adressLine1 = adressLine1;
		}

		public String getAdressline2() {
			return adressline2;
		}

		public void setAdressline2(String adressline2) {
			this.adressline2 = adressline2;
		}

		public String getClientDeclaration() {
			return clientDeclaration;
		}

		public void setClientDeclaration(String clientDeclaration) {
			this.clientDeclaration = clientDeclaration;
		}

		public String getClientDescriptionForInvoice() {
			return clientDescriptionForInvoice;
		}

		public void setClientDescriptionForInvoice(String clientDescriptionForInvoice) {
			this.clientDescriptionForInvoice = clientDescriptionForInvoice;
		}

		public String getProjectDescription() {
			return projectDescription;
		}

		public void setProjectDescription(String projectDescription) {
			this.projectDescription = projectDescription;
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

}
