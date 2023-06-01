package com.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.Booking_info;
import com.demo.model.User;

public interface booking_info_repo extends CrudRepository<Booking_info, Integer> {

	Iterable<Booking_info> findByuid(User u);

}
