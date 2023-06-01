package com.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Query {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	String msg;
	
	@ManyToOne()
	@JoinColumn(name="u_id")
	private User uid;
	
	@OneToMany(mappedBy = "qid")
	List<answer> a;
	
	
	
}
