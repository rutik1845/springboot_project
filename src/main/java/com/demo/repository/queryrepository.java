package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.Query;

public interface queryrepository extends CrudRepository<Query, Integer>{
	Query findByuid(int id);
}
