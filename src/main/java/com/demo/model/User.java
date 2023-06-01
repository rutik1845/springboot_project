package com.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Size(min=3,message = "name should be more than 3 character")
	private String name;
	
	@Column(unique = true)
	@NotNull
	@Email
	private String email;
	
	@NotNull
//	@Pattern(regexp = "[0-9]+",message = "mobile number should be contain only numbers")
	@Size(max = 10,message = "mobile number should be 10 digit long")
	private String contact;
	
	@NotNull
	//@Pattern(regexp = "[A-Za-z0-9!@#$$%^&*]+",message = "password should contain character,digit and special character")
	private String password;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany(mappedBy = "uid",cascade = CascadeType.ALL)
	List<Booking_info> bi;
	
	@OneToMany(mappedBy = "uid", cascade = CascadeType.ALL)
	List<Query> q;
	
	
	
}
