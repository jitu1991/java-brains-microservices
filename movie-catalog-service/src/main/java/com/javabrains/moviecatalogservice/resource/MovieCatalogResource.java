package com.javabrains.moviecatalogservice.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.javabrains.moviecatalogservice.model.CatalogItem;
import com.javabrains.moviecatalogservice.model.UserRating;
import com.javabrains.moviecatalogservice.service.MovieInfo;
import com.javabrains.moviecatalogservice.service.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	WebClient.Builder webClientBuilder;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;

	/*Since spring works on proxy pattern, method calls having hystrix enabled in same class will not work as circuit breaker, 
	It should be part of some spring component to implement as ciruit breaker. That's why the hystrix command and corresponding 
	fallback are moved to service class and expliitly called.*/
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {
		UserRating ratings = userRatingInfo.getUserRating(userId);
		return ratings.getUserRating().stream().map(rating -> movieInfo.getCatalogItem(rating)).collect(Collectors.toList());
	}
}
