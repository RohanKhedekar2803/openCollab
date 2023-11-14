package com.example.openCollab.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lock")
public class test2 {
	
	@GetMapping("user")
	public String print() {
		return "hey its home page for user";
	}
	
	@GetMapping("admin")
	public String print1() {
		return "hey its home page for admin";
	}
}
