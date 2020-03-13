package com.javabrains.springbootconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class GreetingsController {
	
	@Value("${greeting.message}")
	public String greetingMessage;
	
	@GetMapping("/{userName}")
	public String greetUser(@PathVariable String userName) {
		return greetingMessage + " " + userName;
	}
}
