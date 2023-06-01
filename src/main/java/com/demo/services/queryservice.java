package com.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Query;
import com.demo.repository.queryrepository;

@Service
public class queryservice {
	@Autowired
	queryrepository qr;
	
	public Query save(Query q) {
		
		return this.qr.save(q);
	}
	
	public Query findbyuid(int id) {
		return this.qr.findByuid(id);
	}

}
