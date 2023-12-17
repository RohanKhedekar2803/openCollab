package com.example.openCollab.WebSecurity.Models;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
	
	public String username;
	public String password;
	public String email;

}
