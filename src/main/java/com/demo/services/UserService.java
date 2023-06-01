package com.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.exception.file_exception;
import com.demo.model.Booking_info;
import com.demo.model.User;
import com.demo.model.booking_details;
import com.demo.model.hotel;
import com.demo.repository.Booking_detailRepository;
import com.demo.repository.HotelRepository;
import com.demo.repository.UserRepository;
import com.demo.repository.booking_info_repo;

@Service
public class UserService {
	@Autowired
	UserRepository ur;
	
	@Autowired
	HotelRepository hr;
	
	@Autowired
	Booking_detailRepository bdr;
	
	@Autowired
	booking_info_repo bi;
	
	public User saveall(User u)
	{
		return this.ur.save(u);
	}
	
	public boolean findbyemail(String email)
	{
	  User us =	this.ur.findByemail(email);
	  if (us.getEmail().equals(email))
	  {return true;}
	  return false;
	}
	
	public User finduseremail(String email) {
		User us = this.ur.findByemail(email);
		return us;
		
	}
	
	
	public Iterable<hotel> getall()
	{
		return this.hr.findAll();
	}
	
	public hotel findbyid(int id)
	{
		return this.hr.findById(id).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"service not found");
		});
		
	}
	
	public booking_details save(booking_details bd)
	{
		//String name,String email,String phone,String room,String check_in,String check_out,int day,long price
		return this.bdr.save(bd);
	}
	
	public List<booking_details> findByEmail(String email) {
		return this.bdr.findByemail(email);
	}
	
	public booking_details findById(int id)
	{
		return this.bdr.findById(id).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Booking not found");
		});
	}
	
	public booking_details changestatus(int id)
	{
		booking_details bd = this.findById(id);
//		bd.setName(bds.getName());
//		bd.setEmail(bds.getEmail());
//		bd.setMember(bds.getMember());
//		bd.setCheck_in(bds.getCheck_in());
//		bd.setPrice(bds.getPrice());
//		bd.setCheck_out(bds.getCheck_out());
		bd.setStatus("Checkout");
		this.bdr.save(bd);
		return bd;
	}
	
	public booking_details deletebooking(int id)
	{
		booking_details bd = this.findById(id);
		this.bdr.deleteById(id);
		return bd;
	}
	
	public hotel findbycategory(String cat)
	{
		return this.hr.findBycategory(cat);
	}
	
	
	public Booking_info saveinfo(Booking_info bi) {
		return this.bi.save(bi);
	}
	
	public Booking_info findinfobyid(int id) {
		return this.bi.findById(id).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"info not found");
		});
	}
	
	public Booking_info deleteinfo(int id) {
		Booking_info b = this.findinfobyid(id);
		this.bi.deleteById(id);
		return b;
	}
	
	
}


