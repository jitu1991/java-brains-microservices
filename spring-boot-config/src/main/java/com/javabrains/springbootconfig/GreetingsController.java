package com.javabrains.springbootconfig;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RefreshScope//To enable refresh of config values
public class GreetingsController {
	
	//@Value("${my.greeting}")//Getting value from properties file
	@Value("${my.greeting: default value}")//In case key doesnt exist in property file, default value will be assigned
	private String greetingMessage;
	
	@Value("Some static message}")//Given string get assigned to variable
	private String staticMessage;

	@Value("${my.list.values}")//convert comma seperated values to list
	private List<String> listValues;
	
	//@Value("#{${dbValues}}")//# considers this as SPEL, spring expression language
	private Map<String, String> dbValues;
	
	@Autowired
	private DbSettings dbSettings;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/{userName}")
	public String greeting(@PathVariable String userName) {
		//return greetingMessage + " " + staticMessage + listValues + dbValues;
		return greetingMessage;//dbSettings.getConnection() + dbSettings.getHost();
	}
	
	@GetMapping("/envdetails")
	public String envDetails() {
		//return greetingMessage + " " + staticMessage + listValues + dbValues;
		return env.toString();
	}

}
