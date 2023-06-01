package com.demo.services;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.fileupload;

import com.demo.exception.file_exception;
import com.demo.model.Booking_info;
import com.demo.model.User;
import com.demo.model.admin;
import com.demo.model.hotel;
import com.demo.repository.AdminRepository;
import com.demo.repository.HotelRepository;
import com.demo.repository.UserRepository;
import com.demo.repository.booking_info_repo;

@Service
public class AdminService {
	@Autowired
	HotelRepository hr;
	
	@Autowired
	AdminRepository ar;
	
	@Autowired
	UserRepository ur;
	
	@Autowired
	booking_info_repo bir;
	
private Path rootlocation;
	
	public AdminService(fileupload fu)
	{
		this.rootlocation = Paths.get(fu.getUploadDir());
		try {
			Files.createDirectories(rootlocation);
		}catch(Exception e)
		{
			throw new file_exception("could not initialize the directory");
		}
	}
	
	public String saveall(String category,int available_room,long price,MultipartFile file)
	{
		try {
			if(file.isEmpty())
			{
				throw new file_exception("file is empty");
			}
			Path destinationfile = this.rootlocation.resolve(Paths.get(file.getOriginalFilename()));
			try(InputStream inputstream = file.getInputStream()){
				Files.copy(inputstream, destinationfile, StandardCopyOption.REPLACE_EXISTING);
			}
			hotel h = new hotel();
			h.setCategory(category);
			h.setAvailable_room(available_room);
			h.setPrice(price);
			String fileuploaduri = ServletUriComponentsBuilder.fromCurrentContextPath().path("admin/download/").path(file.getOriginalFilename()).toUriString();
			h.setImg(fileuploaduri);
			this.hr.save(h);
			return "file upload successfully";
		}catch(Exception e) {
			throw new file_exception("could not store file");
		}
		
	}
	
	public org.springframework.core.io.Resource loaders(String filename)
	{
		Path file = rootlocation.resolve(filename);
		try 
		{
			org.springframework.core.io.Resource res = new UrlResource(file.toUri());
			if(res.exists()||res.isReadable())
			{
				return res;
			}
			else
			{
				throw new file_exception("Could not read File");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new file_exception("Could not read File");
		}
	}
	
	public Iterable<hotel> getall()
	{
		return this.hr.findAll();
	}
	
	public hotel getbyid(int id)
	{
		return this.hr.findById(id).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "services not found");
		});
	}
	
	public hotel create(hotel h)
	{
		hotel ho = new hotel();
		ho.setCategory(h.getCategory());
		ho.setAvailable_room(h.getAvailable_room());
		ho.setImg(h.getImg());
		ho.setPrice(h.getPrice());
		return this.hr.save(ho);
	}
	
	public String update(hotel hd,MultipartFile file)
	{
		try {
			if(file.isEmpty())
			{
				throw new file_exception("file is empty");
			}
			Path destinationfile = this.rootlocation.resolve(Paths.get(file.getOriginalFilename()));
			try(InputStream inputstream = file.getInputStream()){
				Files.copy(inputstream, destinationfile, StandardCopyOption.REPLACE_EXISTING);
			}
			hotel h = this.getbyid(hd.getId());
			String fileuploaduri = (file.getOriginalFilename()).toString();
			h.setImg(fileuploaduri);
			this.hr.save(h);
			return "file upload successfully";
		}catch(Exception e) {
			throw new file_exception("could not store file");
		}
		
	}
	
	public hotel delete(int id)
	{
		hotel h = this.getbyid(id);
		this.hr.deleteById(id);
		return h;
	}
	
	public boolean findbyname(String name)
	{
	  admin us =	this.ar.findByname(name);
	  if (us.getName().equals(name))
	  {return true;}
	  return false;
	}
	
	public Iterable<User> getalluser()
	{
		return this.ur.findAll();
	}
	
	public User findbyid(int id)
	{
		return this.ur.findById(id).orElseThrow(()-> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found");
		});
	}
	
	public User deletebyid(int id)
	{
		User u = this.findbyid(id);
		this.ur.deleteById(id);
		return u;
		
	}
	
	public Iterable<Booking_info> getallinfo() {
		return this.bir.findAll();
		
	}
	
	public Booking_info findinfobyid(int id) {
		return this.bir.findById(id).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"not found");
		});
	}
	
	public Booking_info deleteinfobyid(int id) {
		Booking_info bi = this.findinfobyid(id);
		this.bir.deleteById(id);
		return bi;
	}
	
	
	
}
