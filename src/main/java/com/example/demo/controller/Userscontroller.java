package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Users;
import com.example.demo.services.UsersServices;

import jakarta.servlet.http.HttpSession;



@Controller
public class Userscontroller {
	@Autowired
	UsersServices service;
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user) {
		boolean userStatus = service.emailExists(user.getEmail());
		if(userStatus==false) {
			service.addUser(user);
			System.out.println("user  is added");
		}
		else {
			System.out.println("User already exist");
		}
		return "home";
	}

	@PostMapping("/validate")
	public String validateUser(@RequestParam("email") String email,
			@RequestParam ("password")String password, HttpSession session) {
		if(service.validateUser(email,password)==true) {
			String role=service.getRole(email);
			
			session.setAttribute("email",email);

			if(role.equals("admin")) {
				return "adminHome";
			}
			else {
				return "customerHome";
			}
		}
		else 
		{
			return "login";
		}
	}


	//// removed this section because of the pay implemenation down by PaymentController page
//	@GetMapping("/pay")
//	public String pay(@RequestParam String email) {
//		
//		boolean paymentStatus = false;
//		
//		if(paymentStatus == true) {
//			Users user = service.getUser(email);
//			user.setPremium(true);
//			service.updateUser(user);
//		}
//		return "login";
//	}
//	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
}
