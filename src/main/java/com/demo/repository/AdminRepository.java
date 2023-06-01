package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.admin;

public interface AdminRepository extends CrudRepository<admin, Integer>{
	admin findByname(String name);
}
