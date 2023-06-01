package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.demo.model.Booking_info;
import com.demo.model.Query;
import com.demo.model.User;
import com.demo.model.hotel;
import com.demo.repository.HotelRepository;
import com.demo.repository.answerrepository;
import com.demo.repository.booking_info_repo;
import com.demo.repository.queryrepository;
import com.demo.services.AdminService;
import com.demo.services.queryservice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	AdminService as;
	
	@Autowired
	HotelRepository hr;
	
	@Autowired
	booking_info_repo bir;
	
	@Autowired
	queryrepository qr;
	
	@Autowired
	queryservice qs;
	
	@Autowired
	answerrepository ar;

	
	@GetMapping("")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("file_upload");
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView login()
	{
		ModelAndView mv = new ModelAndView("adminlogin");
		return mv;
	}
	
	@PostMapping("/loginverify")
	public ModelAndView login(@RequestParam("name") String name)
	{
		try {
			boolean b = this.as.findbyname(name);
			if(b==true) {
				ModelAndView mv = new ModelAndView("adminoption");
				return mv;
			}
		}catch(Exception e) {
			ModelAndView mv = new ModelAndView("adminlogin");
			 return mv.addObject("msg", "plzz enter valid name");
		}
		return null;
	}
	
	@GetMapping("/addhotel")
	public ModelAndView addhotel()
	{
		ModelAndView mv = new ModelAndView("addhotel");
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView save(@RequestParam("category") String category,@RequestParam("available_room") int available_room,@RequestParam("price") long price,@RequestParam("file") MultipartFile file)
	{
		try {
			ModelAndView mv = new ModelAndView("addhotel");
			as.saveall(category, available_room, price, file);
			mv.addObject("msg","save");
			return mv;
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return null;
	}
	
	@GetMapping("/download/{filename}")
	public ResponseEntity<?> downloadfile(@PathVariable String filename)
	{
		Resource resource = this.as.loaders(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename\""+filename+"\"").body(resource);
	}
	
	@GetMapping("/get")
	public ModelAndView getall()
	{
		ModelAndView mv = new ModelAndView("crud");
		Iterable<hotel> h = this.as.getall();
		mv.addObject("h", h);
		return mv;
	}
	
	@GetMapping("/getid/{hid}")
	public ModelAndView getbyid(@PathVariable("hid") int id)
	{
		ModelAndView mv = new ModelAndView("hotelupdate");
		hotel h = this.as.getbyid(id);
		mv.addObject("h", h);
		return mv;
	}
	
	@PostMapping("/update/{hid}")
	public ModelAndView update(@PathVariable("hid") int id,@RequestParam("category") String category,@RequestParam("ar") int available_room,@RequestParam("price") long price,@RequestParam("file") MultipartFile file)
	{
		
		hotel h=this.as.getbyid(id);
		h.setCategory(category);
		h.setPrice(price);
		h.setAvailable_room(available_room);
		this.as.update(h, file);
		ModelAndView mv = new ModelAndView("redirect:../get");
		return mv;
	}
	
	@PostMapping("/delete/{hid}")
	public ModelAndView delete(@PathVariable("hid") int id)
	{
		hotel h = this.as.delete(id);
		ModelAndView mv = new ModelAndView("redirect:../get");
		return mv;
	}
	
	@GetMapping("/logout")
	public ModelAndView logout()
	{
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}
	
	@GetMapping("/getuser")
	public ModelAndView getalluser()
	{
		ModelAndView mv = new ModelAndView("getuser");
		Iterable<User> u = this.as.getalluser();
		mv.addObject("u", u);
		return mv;
	}
	
	@PostMapping("/deluser/{uid}")
	public ModelAndView deleteuser(@PathVariable("uid") int id)
	{
		User u = this.as.deletebyid(id);
		ModelAndView mv = new ModelAndView("redirect:../getuser");
		return mv;
	}
	
	@GetMapping("/getorder")
	public ModelAndView getorder()
	{
		ModelAndView mv = new ModelAndView("getorder");
		Iterable<Booking_info> bi = this.as.getallinfo();
		return mv.addObject("b",bi);
	}
	
	@GetMapping("/checkout/{bid}")
	public ModelAndView checkout(@PathVariable("bid") int id)
	{
		
		Booking_info boi =  this.as.findinfobyid(id);
		
		Integer hid = boi.getHid().getId();
			
			int numRoom = boi.getNumber_of_rooms();
			hotel h = this.as.getbyid(hid);
			int val = h.getAvailable_room();
			h.setPrice(h.getPrice());
			h.setImg(h.getImg());
			h.setCategory(h.getCategory());
			h.setAvailable_room(val+numRoom);
			hr.save(h);
			boi.setStatus("Checkout");
			bir.save(boi);
			
		
		
		ModelAndView mv = new ModelAndView("redirect:../getorder");
		return mv;
//		Booking_info bi = this.us.deleteinfo(id);
	}
	
	@GetMapping("delete/{bid}")
	public ModelAndView deleteinfo(@PathVariable("bid") int id) {
		Booking_info bi = this.as.deleteinfobyid(id);
		
		Integer uid = bi.getUid().getId();
		
		User u = this.as.deletebyid(uid);
		
		ModelAndView mv = new ModelAndView("getorder");
		return mv;
		
	}
	
	@GetMapping("getquestion")
	public ModelAndView getquestion() {
		Iterable<Query> q = this.qr.findAll();
		ModelAndView mv = new ModelAndView("getquery");
		mv.addObject("q",q);
		return mv;
	}
	
//	@PostMapping("submit/{qid}")
//	public ModelAndView getanswer(@RequestParam("qid") int qid, @RequestParam("name") String name, @RequestParam("query") String query, @RequestParam("ans") String msg)
//	{
//	}
}
