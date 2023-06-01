package com.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	String ans;
	
	@ManyToOne()
	@JoinColumn(name="queryid")
	private Query qid;

}
