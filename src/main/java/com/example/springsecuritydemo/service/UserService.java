package com.example.springsecuritydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecuritydemo.model.Users;
import com.example.springsecuritydemo.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public String verify(Users user) {
		
		Authentication authentication =  authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
	    if(authentication.isAuthenticated())
	    {
	    	return jwtService.generateToken(user.getUsername());
	    }
	    return "Fail";
	}

}
