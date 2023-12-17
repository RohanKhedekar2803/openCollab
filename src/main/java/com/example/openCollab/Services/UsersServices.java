package com.example.openCollab.Services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.openCollab.Entities.User;
import com.example.openCollab.Repositories.UserRepository;
import com.example.openCollab.WebSecurity.Models.AuthenticationRequest;
import com.example.openCollab.WebSecurity.Models.AuthenticationResponse;
import com.example.openCollab.WebSecurity.Models.RegisterRequest;
import com.example.openCollab.WebSecurity.Models.Role;
import com.example.openCollab.WebSecurity.Models.updateUserRequest;
import com.example.openCollab.WebSecurity.jwt.JwtService;


@Component
public class UsersServices {

	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userrepository;
	
	
	
	
	
	public AuthenticationResponse register(RegisterRequest request) {
		
		Role role;
		if(request.getRole().equals("user") || request.getRole().equals("USER") || request.getRole().equals("User")) {
			role=Role.USER;
		}else {
			role=Role.ADMIN;
		}
		if(request.getFirstname().equals("") || request.getLastname().equals("")
				|| request.getUsername().equals("")) {
			return null;
		}
		User emailcheck=userrepository.findByEmail(request.getEmail());
		User usernamecheck=userrepository.findByUsername(request.getUsername());
		if(usernamecheck!=null || emailcheck!=null){
			return null;
		}
		User user = User.builder().email(request.getEmail())
				.username(request.getUsername())
				.password(request.getPassword())
				.role(role)
				.address(request.getAddress())
				.city(request.getCity())
				.firstName(request.getFirstname())
				.lastName(request.getLastname())
				.phone(request.getPhone())
				.street(request.getStreet())
				.state(request.getState())
				.build();
		
		userrepository.save(user);
		
		var jwtToken=jwtService.generateToken(user.getUsername());
		
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		System.out.println("auth started");
		User user =userrepository.findByUsername(request.getUsername());
		
		if(user==null || !user.getPassword().equals(request.getPassword())) {
			return null;
		}
		
		var jwtToken=jwtService.generateToken(user.getUsername());
		
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public ArrayList<User> getUsers() {
		
		return userrepository.findAll();
	}

	public String deleteuser(Long uuid) {
		Optional<User> optionaluser =userrepository.findById(uuid);
	
		if (optionaluser.isPresent()) {
            
            User user = optionaluser.get();
            userrepository.delete(user);
            
            //if entity is deleted 
            optionaluser =userrepository.findById(uuid);
            if (!optionaluser.isPresent()) {
            	return "Successfully deleted";
            }else {                           //if user is deleted 
            	return "Error Not deleted";
            }
            
        } 
          
		return "UUID not found";
	}

	
	public String updateuser(Long uuid, updateUserRequest requestuser) {
		
		 if(requestuser==null) {
         	return "Body is Empty";
         }
		 
		 
		 Optional<User> optionaluser =userrepository.findById(uuid);
		if (optionaluser.isPresent()) {
            
            User user = optionaluser.get();
            user.setAddress(requestuser.getAddress());
            user.setCity(requestuser.getCity());
            user.setEmail(requestuser.getEmail());
            user.setFirstName(requestuser.getFirstname());
            user.setLastName(requestuser.lastname);
            user.setState(requestuser.getState());
            user.setStreet(requestuser.getStreet());
            user.setAddress(requestuser.getAddress());
            userrepository.save(user);
            
            //if entity is updated 
            optionaluser =userrepository.findById(uuid);
            if (!optionaluser.equals(user)) {
            	return "Successfully updated";
            }
		}
		return "UUID not found";
	}

}
