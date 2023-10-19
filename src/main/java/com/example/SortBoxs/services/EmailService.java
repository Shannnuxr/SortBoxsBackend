package com.example.SortBoxs.services;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Calendar;

@Service
public class EmailService {

	@Autowired
	 private JavaMailSender javaMailSender;
	
	 private final ResourceLoader resourceLoader;

	    public EmailService(ResourceLoader resourceLoader) {
	        this.resourceLoader = resourceLoader;
	    }
	
	  
	private final String senderEmail = "srinivasshanmukha5@gmail.com";
	
//	@Autowired
//	  public EmailService(JavaMailSender javaMailSender, String senderEmail) {
//	        this.javaMailSender = javaMailSender;
//	        this.senderEmail = senderEmail;
//	    }
	  
	  public void sendActivationLink(String email, String firstName, String activationLink, String otp) {
		  String templatePath = "classpath:/templates/activate-account.html";
	        String subject = "Activate Your Account";
	        
	        String emailContent = loadActivationEmailTemplate(templatePath);

	        sentEmailWithTemplateOtp(email, firstName, subject, activationLink, otp,emailContent);
	    }
	  public void sentEmailWithTemplateOtp(String email, String firstName, String subject, String url, String otp, String content) {

	        String senderName = "Qloron Firm";
	        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

	        try {
	            MimeMessage message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	            helper.setFrom(senderEmail, senderName);
	            helper.setTo(email);
	            helper.setSubject(subject);

	            // Load email template from file
//	            ClassPathResource resource = new ClassPathResource(template);
//	            String content = new String(Files.readAllBytes(resource.getFile().toPath()));

	            // Replace placeholders in email template with dynamic content
	            content = content.replace("{{firstName}}", firstName);
	            content = content.replace("{{activationLink}}", url);
	            content = content.replace("{{currentYear}}", currentYear);
	            
	            content = content.replace("{{otp}}", otp);


	            helper.setText(content, true);

	            javaMailSender.send(message);

	        } catch (MessagingException | IOException exception) {
	            exception.printStackTrace();
	        }
	  }
	  
	    private String loadActivationEmailTemplate(String templatePath) {
	        try {
	            Resource resource = resourceLoader.getResource(templatePath);
	            InputStream inputStream = resource.getInputStream();
	            // Read the content of the template file from the input stream and return it as a string
	            return readInputStreamToString(inputStream);
	        } catch (IOException e) {
	            // Handle the exception or return an appropriate default content
	            return ""; // Return an empty string or an appropriate default content
	        }
	    }
	    
	    private String readInputStreamToString(InputStream inputStream) throws IOException {
	    	  StringBuilder stringBuilder = new StringBuilder();
	    	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
	    	        String line;
	    	        while ((line = reader.readLine()) != null) {
	    	            stringBuilder.append(line);
	    	        }
	    	    }
	    	    return stringBuilder.toString();

	    }
	  
	  public void sendResetPasswordRequestToUser(String email, String firstName, String resetPasswordLink) {
	        String RESET_PASSWORD_EMAIL_TEMPLATE = "templates/reset-password.html";
	        String subject = "Reset Your Password";

	        sentEmailWithTemplate(email, firstName, subject, resetPasswordLink, RESET_PASSWORD_EMAIL_TEMPLATE);
	    }

	  
	  
	 
	  public void sentEmailWithTemplate(String email, String firstName, String subject, String url, String template ) {

	        String senderName = "Qloron Firm";
	        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

	        try {
	            MimeMessage message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	            helper.setFrom(senderEmail, senderName);
	            helper.setTo(email);
	            helper.setSubject(subject);

	            // Load email template from file
	            ClassPathResource resource = new ClassPathResource(template);
	            String content = new String(Files.readAllBytes(resource.getFile().toPath()));

	            // Replace placeholders in email template with dynamic content
	            content = content.replace("{{firstName}}", firstName);
	            content = content.replace("{{activationLink}}", url);
	            content = content.replace("{{currentYear}}", currentYear);
	            
	           // content = content.replace("{{otp}}", otp);


	            helper.setText(content, true);

	            javaMailSender.send(message);

	        } catch (MessagingException | IOException exception) {
	            exception.printStackTrace();
	        }
	    }

}

	
	


	
	

