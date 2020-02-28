package com.javabrains.ratingsdataservice.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javabrains.ratingsdataservice.model.Rating;
import com.javabrains.ratingsdataservice.model.UserRating;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId) {
		return new Rating(movieId, 4);
	}
	
	/*Avoid returning list in response*/
	@RequestMapping("/{userId}")
	public UserRating getUserRating(@PathVariable String userId) {
		List<Rating> ratings = Arrays.asList(new Rating("1234", 4),
				new Rating("5678",3));
		
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
}
