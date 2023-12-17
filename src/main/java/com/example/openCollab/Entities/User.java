package com.example.openCollab.Entities;

import com.example.openCollab.WebSecurity.Models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	public Long id;
	public String username;
	public String password;
	public String email;
	public String firstName;
	public String lastName;
	public String state;
	public String street;
	public String address;
	public String city;
	public String phone;
	
	
	// by default enum ype is ordinal setting it to strinbg will take string value in it
	@Enumerated(EnumType.STRING)
	public Role role;
}
