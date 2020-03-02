package com.javabrains.moviecatalogservice.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.javabrains.moviecatalogservice.model.CatalogItem;
import com.javabrains.moviecatalogservice.model.Movie;
import com.javabrains.moviecatalogservice.model.Rating;
import com.javabrains.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	WebClient.Builder webClientBuilder;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {
		/*UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratings/" + userId,
				UserRating.class);*/
		//Call through eureka
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratings/" + userId,
				UserRating.class);

		return ratings.getUserRating().stream().map(rating -> {
			// For each movie Id, call movie-info-service and get details
			//Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(), "Test", rating.getRating());
		}).collect(Collectors.toList());

		// To Build web client
		// WebClient.Builder builder = WebClient.builder();

		// Using weclient builder
		// Movie movie =
		// webClientBuilder.build().get().uri("http://localhost:8081/movies/" +
		// rating.getMovieId()).retrieve().bodyToMono(Movie.class).block();
		// return Collections.singletonList(new CatalogItem(userId, "Transformer", 3));
	}
}
