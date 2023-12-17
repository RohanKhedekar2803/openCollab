package com.example.openCollab.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.openCollab.Entities.User;
import com.example.openCollab.Services.UsersServices;
import com.example.openCollab.WebSecurity.Models.AuthenticationRequest;
import com.example.openCollab.WebSecurity.Models.AuthenticationResponse;
import com.example.openCollab.WebSecurity.Models.RegisterRequest;
import com.example.openCollab.WebSecurity.Models.updateUserRequest;

@RestController
@RequestMapping("sunbase/portal/api/")
public class UsersController {
	
	
	@Autowired
	private UsersServices service;
	
	@PostMapping("assignment_auth.jsp")
	public ResponseEntity<String> auth(@RequestBody AuthenticationRequest request) {
			System.out.println("in auth");	
			AuthenticationResponse authresponse=service.authenticate(request);
			if(authresponse==null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Password is Wrong");
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(authresponse.getToken());

	}
	
	@GetMapping("assignment.jsp")
	public ArrayList<User> getCustomerList(
			@RequestBody RegisterRequest request) {

		return service.getUsers();
	}
	
	
	@PostMapping("assignment.jsp")
	public ResponseEntity<String> register(
			@RequestBody RegisterRequest request) {
		
		AuthenticationResponse authresponse=service.register(request);
		if(authresponse==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Either First Name or Last Name is missing or username or email already exist");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(authresponse.getToken());
	}
	
	@DeleteMapping("assignment.jsp")
	public ResponseEntity<String> deleteUser(@RequestParam Long uuid){
		String status=service.deleteuser(uuid);
		if(status.equals("Successfully deleted")) {
			return  ResponseEntity.status(HttpStatus.OK).body(status);
		}else if(status.equals("UUID not found")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
		}
		
	}
	
	@PutMapping("assignment.jsp")
	public ResponseEntity<String> updateUser(@RequestParam Long uuid , @RequestBody updateUserRequest requestuser){
		String status=service.updateuser(uuid,requestuser);
		if(status.equals("Successfully updated")) {
			return  ResponseEntity.status(HttpStatus.OK).body(status);
		}else if(status.equals("UUID not found")) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
		}
		
	}
}
