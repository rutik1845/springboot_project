package com.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class booking_details {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String name;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Pattern(regexp = "[0-9]+",message = "mobile number should be contain only numbers")
	@Size(max = 10,message = "mobile number should be 10 digit long")
	private String phone;
	
	@NotNull
	private int number_of_rooms;
	
	@NotNull
	private String room_type;
	
	@NotNull
	private String check_in;
	
	@NotNull
	private String check_out;
	
	@NotNull
	//@Pattern(regexp = "[0-9]+",message = "numbers")
	private String member;
	
	@NotNull
	private int days;
	
	private long price;
	
	@Column(columnDefinition = "varchar(255) default 'Reserve'")
	private String status;
	
	//private LocalDate check_out;
	
	

}
