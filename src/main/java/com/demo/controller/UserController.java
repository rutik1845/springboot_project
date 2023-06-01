package com.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.demo.model.Booking_info;
import com.demo.model.User;
import com.demo.model.booking_details;
import com.demo.model.hotel;
import com.demo.repository.HotelRepository;
import com.demo.repository.UserRepository;
import com.demo.repository.booking_info_repo;
import com.demo.services.AdminService;
import com.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService us;
	
	@Autowired
	UserRepository ur;
	
	@Autowired
	HotelRepository as;
	
	@Autowired
	booking_info_repo bir;
	
	@GetMapping("")
	public String index()
	{
		return "index";
	}
	
	@GetMapping("/ho")
	public ModelAndView first(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session==null)
		{
			ModelAndView mv = new ModelAndView("login");
			return mv.addObject("msg", "please login");
		}
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
	@GetMapping("/log")
	public ModelAndView login()
	{
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	@GetMapping("/reg")
	public ModelAndView home()
	{
		ModelAndView mv = new ModelAndView("registration");
		return mv;
	}
	
	@PostMapping("/register")
	public ModelAndView save(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("contact") String contact,@RequestParam("pass") String pass,HttpServletRequest request)
	{
		try {
			User u = new User();
			 u.setName(name);
			 u.setEmail(email);
			 u.setContact(contact);
			 u.setPassword(pass);
			 
			 this.us.saveall(u);
			 
			 ModelAndView mv = new ModelAndView("login");
			 return mv.addObject("msg", "user register");
		}catch(Exception e) {
			ModelAndView mv = new ModelAndView("login");
			 return mv.addObject("msg", "user already register");
		}
		 
	}
	
	@PostMapping("/login")
	public ModelAndView login(@RequestParam("email") String email,HttpServletRequest request)
	{
		try {
			boolean b = this.us.findbyemail(email);
			if(b==true) {
				HttpSession session = request.getSession(true);
				session.setAttribute("email", email);
				ModelAndView mv = new ModelAndView("home");
				return mv;
			}
		}catch(Exception e) {
			ModelAndView mv = new ModelAndView("registration");
			 return mv.addObject("msg", "register please");
		}
		return null;
	}
	
	@GetMapping("/service")
	public ModelAndView service(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session==null)
		{
			ModelAndView mv = new ModelAndView("login");
			return mv.addObject("msg", "please login");
		}
		Iterable<hotel> h = this.us.getall();
		ModelAndView mv = new ModelAndView("service");
		mv.addObject("hotel", h);
		return mv;
	}
	
	@GetMapping("/getbook/{hotelid}")
	public ModelAndView getbook(@PathVariable("hotelid") String id,HttpServletRequest request)
	{
		int h_id=Integer.parseInt(id);
		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		User u = this.us.finduseremail(email);
		
		if(session==null)
		{
			ModelAndView mv = new ModelAndView("login");
			return mv.addObject("msg", "please login");
		}
		try {
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		hotel h = this.us.findbyid(h_id);
////		int sid = Integer.parseInt(id);
////		System.out.println(sid);
//		String category = h.getCategory();
//		String available_room = h.getAvailable_room();
//		long price = h.getPrice();
		
		ModelAndView mv = new ModelAndView("book");
		mv.addObject("h", h);
		mv.addObject("u",u);
		
		return mv;
	}
	
	//booking detail
	
	@PostMapping("/booking/{hid}/{uid}")
	public ModelAndView booking_detail(@PathVariable("hid")int hid,@PathVariable("uid")int uid,@RequestParam("RoomNo")String rm,@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("phone") String phone, @RequestParam("room-type") String room,@RequestParam("check_in") String check_in,@RequestParam("check_out") String check_out,@RequestParam("member") String member,@RequestParam("price") long price,@RequestParam("daysDiff") String daysDiff,HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		User u = ur.findById(uid).orElseThrow(()->{throw new ResponseStatusException(HttpStatus.NOT_FOUND,"service not found");});
		int dayDiff = Integer.parseInt(daysDiff);
		int numRoom= Integer.parseInt(rm);
		System.out.println(daysDiff); 
		if(room.equalsIgnoreCase("Ac-room")) {
			hotel h = this.us.findbyid(hid);
			int val = h.getAvailable_room();
			h.setPrice(h.getPrice());
			h.setImg(h.getImg());
			h.setCategory(h.getCategory());
			h.setAvailable_room(val-numRoom);
			as.save(h);
			Booking_info bi = new Booking_info();
			bi.setHid(h);
			bi.setUid(u);
			bi.setCheck_in(check_in);
			bi.setCheck_out(check_out);
			bi.setNumber_of_rooms(numRoom);
			bi.setDays(dayDiff);
			bi.setMember(member);
			bi.setPrice(numRoom*(dayDiff*1500));
			this.us.saveinfo(bi);
			ModelAndView mv = new ModelAndView("redirect:../../home");
			return mv;
			
//		booking_details bd = new booking_details();
//		bd.setName(name);
//		bd.setEmail(email);
//		bd.setDays(dayDiff);
//		bd.setPhone(phone);
//		bd.setNumber_of_rooms(numRoom);
//		bd.setRoom_type(room);
//		bd.setCheck_in(check_in);
//		bd.setCheck_out(check_out);
//		bd.setMember(member);
//		bd.setPrice(numRoom*(dayDiff*1500));
//		this.us.save(bd);
		}
		else if(room.equalsIgnoreCase("Non-Ac room")){
			hotel h = this.us.findbyid(hid);
			int val = h.getAvailable_room();
			h.setPrice(h.getPrice());
			h.setImg(h.getImg());
			h.setCategory(h.getCategory());
			h.setAvailable_room(val-numRoom);
			as.save(h);
			Booking_info bi = new Booking_info();
			bi.setHid(h);
			bi.setUid(u);
			bi.setCheck_in(check_in);
			bi.setCheck_out(check_out);
			bi.setNumber_of_rooms(numRoom);
			bi.setDays(dayDiff);
			bi.setMember(member);
			bi.setPrice(numRoom*(dayDiff*800));
			this.us.saveinfo(bi);
			ModelAndView mv = new ModelAndView("redirect:../../home");
			return mv;
		}
		else {
			hotel h = this.us.findbyid(hid);
			int val = h.getAvailable_room();
			h.setPrice(h.getPrice());
			h.setImg(h.getImg());
			h.setCategory(h.getCategory());
			h.setAvailable_room(val-numRoom);
			as.save(h);
			Booking_info bi = new Booking_info();
			bi.setHid(h);
			bi.setUid(u);
			bi.setCheck_in(check_in);
			bi.setCheck_out(check_out);
			bi.setNumber_of_rooms(numRoom);
			bi.setDays(dayDiff);
			bi.setMember(member);
			bi.setPrice(numRoom*(dayDiff*2500));
			this.us.saveinfo(bi);
			ModelAndView mv = new ModelAndView("redirect:../../home");
			return mv;
		}
	}
	
	@GetMapping("home")
	public ModelAndView homepage(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		return new ModelAndView("home");
	}
	
	@GetMapping("offer")
	public ModelAndView offerpage(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		return new ModelAndView("offer");
	}
	
	@GetMapping("/details")
	public ModelAndView details(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session==null)
		{
			ModelAndView mv = new ModelAndView("login");
			return mv.addObject("msg", "please login");
		}
		String email = (String) session.getAttribute("email");
		User u = this.us.finduseremail(email);
		Iterable<Booking_info> bd = this.bir.findByuid(u);
		ModelAndView mv = new ModelAndView("conform_order");
		mv.addObject("bd", bd);
		mv.addObject("u", u);
		return mv;
		
	}
	
	@GetMapping("/changestatus/{bid}")
	public ModelAndView changestatus(@PathVariable("bid") int id,HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		booking_details bd = this.us.changestatus(id);
		
		int numRoom = bd.getNumber_of_rooms();
		
//		hotel ho = new hotel();
//		int hid = ho.getId();
		hotel h = this.us.findbycategory(bd.getRoom_type());
		int val = h.getAvailable_room();
		h.setPrice(h.getPrice());
		h.setImg(h.getImg());
		h.setCategory(h.getCategory());
		h.setAvailable_room(val+numRoom);
		as.save(h);
		
		ModelAndView mv = new ModelAndView("statusresponse");
		mv.addObject("msg", "your status is change to checkout, thank you for visited our hotel");
		return mv;
		
	}
	
	@GetMapping("/deletebooking/{bid}")
	public ModelAndView deletebooking(@PathVariable("bid") int id,HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		Booking_info boi =  this.us.findinfobyid(id);
		
		Integer hid = boi.getHid().getId();
			
			int numRoom = boi.getNumber_of_rooms();
			hotel h = this.us.findbyid(hid);
			int val = h.getAvailable_room();
			h.setPrice(h.getPrice());
			h.setImg(h.getImg());
			h.setCategory(h.getCategory());
			h.setAvailable_room(val+numRoom);
			boi.setStatus("Cancle");
			as.save(h);
		
		
		ModelAndView mv = new ModelAndView("statusresponse");
		mv.addObject("msg", "your booking is cancel, thank you for visited our hotel");
		return mv;
//		Booking_info bi = this.us.deleteinfo(id);
	}
	
	@GetMapping("/returnhome")
	public ModelAndView returnhome(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
		
	}
	
	@GetMapping("contact")
	public ModelAndView contactpage(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		User u = this.us.finduseremail(email);
		if(session==null)
		{
			ModelAndView mv = new ModelAndView("login");
			return mv.addObject("msg", "please login");
		}
		ModelAndView mv = new ModelAndView("contact-us");
		return mv.addObject("u",u);
	}
	
	@GetMapping("about")
	public ModelAndView aboutpage(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		return new ModelAndView("aboutus");
	}
	
	
}
