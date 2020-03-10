package com.javabrains.moviecatalogservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.javabrains.moviecatalogservice.model.CatalogItem;
import com.javabrains.moviecatalogservice.model.Rating;
import com.javabrains.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfo {

	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="2000"),//Wait for this much for timeout
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="5"),//no of request to analyze
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="50"),//percentage of request failed
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="5000")//how long circuit breaker sleeps before resume
			})
	public UserRating getUserRating(@PathVariable String userId) {
		return restTemplate.getForObject("http://ratings-data-service/ratings/" + userId,
				UserRating.class);
	}
	
	private UserRating getFallbackUserRating(@PathVariable String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(new Rating("0",0)));
		return userRating;
	}
}
