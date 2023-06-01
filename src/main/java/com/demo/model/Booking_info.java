package com.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Booking_info {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	@NotNull
	private String check_in;
	
	@NotNull
	private String check_out;
	
	@NotNull
	private int number_of_rooms;
	
	@NotNull
	//@Pattern(regexp = "[0-9]+",message = "numbers")
	private String member;
	
	@NotNull
	private int days;
	
	private long price;
	
	@Column(columnDefinition = "varchar(255) default 'Reserve'")
	private String status;
	
	@ManyToOne()
	@JoinColumn(name="user_id")
	private User uid;
	
	@ManyToOne()
	@JoinColumn(name="hotel_id")
	private hotel hid;
}
