package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.model.Query;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.repository.queryrepository;
import com.demo.services.UserService;
import com.demo.services.queryservice;

@RestController
@RequestMapping("/query")
public class querycontroller {
	@Autowired
	UserRepository ur;
	
	@Autowired
	UserService us;
	
	@Autowired
	queryrepository qr;
	
	@Autowired
	queryservice qs;
	
	@PostMapping("/querysave")
	public ModelAndView save(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("phone") String phone,@RequestParam("message") String msg) {
	
		Query q = new Query();
		User u = this.us.finduseremail(email);
		q.setUid(u);
		q.setMsg(msg);
		this.qs.save(q);
		ModelAndView mv = new ModelAndView("redirect:../user/contact");
		return mv;
	}
	
	
}
