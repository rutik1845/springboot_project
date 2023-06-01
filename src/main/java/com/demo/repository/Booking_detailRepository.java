package com.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.booking_details;

public interface Booking_detailRepository extends CrudRepository<booking_details, Integer>{
	List<booking_details> findByemail(String email);
}
