package com.example.openCollab.WebSecurity.Models;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class updateUserRequest {
	public String username;
	public String password;
	public String email;
	public String firstname;
	public String lastname;
	public String state;
	public String street;
	public String address;
	public String city;
	public String phone;
}
