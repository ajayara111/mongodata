package com.example.mongo.rest.service;


import java.util.List;

import com.example.mongo.rest.model.Movie;

public interface MovieService {
	
	Movie findById(long id);
	
	Movie findByName(String name);
	
	void saveMovie(Movie movie);
	
	void updateMovie(Movie movie);
	
	void deleteMovieById(long id);

	List<Movie> findAllMovies();
	
	boolean isMovieExist(Movie movie);
	
}
