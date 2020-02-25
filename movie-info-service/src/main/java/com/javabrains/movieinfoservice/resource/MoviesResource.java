package com.javabrains.movieinfoservice.resource;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javabrains.movieinfoservice.model.Movie;

@RestController
@RequestMapping("/movies")
public class MoviesResource {

	@RequestMapping("/{movieId}")
	public List<Movie> getMovies(String movieId) {
		return Collections.singletonList(new Movie(1, "Transformer"));
	}
}
