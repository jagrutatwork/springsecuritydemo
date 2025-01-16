package com.example.springsecuritydemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecuritydemo.model.Users;
import com.example.springsecuritydemo.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public Users registerUser (@RequestBody Users user) {
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
		return userService.verify(user);
	}
} 
