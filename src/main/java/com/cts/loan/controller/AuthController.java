package com.cts.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.loan.entity.AuthRequest;
import com.cts.loan.entity.AuthResponse;
import com.cts.loan.service.AuthService;
import com.cts.loan.service.JwtUtil;

@RestController

public class AuthController {

	@Autowired
	private JwtUtil jwtutil;

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getUserpassword()));

			final UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());
			final String token = jwtutil.generateToken(userDetails);
			final String username = userDetails.getUsername();
			return new ResponseEntity<>(new AuthResponse(username, token), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		}

	}
}
