package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.hotel;

public interface HotelRepository extends CrudRepository<hotel, Integer>{
 hotel findBycategory(String cat);
}
