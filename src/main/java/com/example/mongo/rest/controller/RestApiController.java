package com.example.mongo.rest.controller;

import java.util.List;

import com.example.mongo.rest.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mongo.rest.service.MovieService;
import com.example.mongo.rest.util.CustomErrorType;

/**
 * creating a movie library in mongo and retrieving / creating those in mongo
 */
@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	MovieService userService; //Service which will do all data retrieval/manipulation work



	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> listAllUsers() {
		List<Movie> movies = userService.findAllUsers();
		if (movies.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}



	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching Movie with id {}", id);
		Movie movie = userService.findById(id);
		if (movie == null) {
			logger.error("Movie with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Movie with id " + id
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}



	@RequestMapping(value = "/movie/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody Movie movie, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Movie : {}", movie);

		if (userService.isUserExist(movie)) {
			logger.error("Unable to create. A Movie with name {} already exist", movie.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Movie with name " +
			movie.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		userService.saveUser(movie);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/movie/{id}").buildAndExpand(movie.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}



	@RequestMapping(value = "/movie/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Movie movie) {
		logger.info("Updating Movie with id {}", id);

		Movie currentMovie = userService.findById(id);

		if (currentMovie == null) {
			logger.error("Unable to update. Movie with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Movie with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentMovie.setName(movie.getName());
		currentMovie.setAge(movie.getAge());
		currentMovie.setSalary(movie.getSalary());

		userService.updateMovie(currentMovie);
		return new ResponseEntity<Movie>(currentMovie, HttpStatus.OK);
	}



	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Movie with id {}", id);

		Movie movie = userService.findById(id);
		if (movie == null) {
			logger.error("Unable to delete. Movie with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Movie with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteMovieById(id);
		return new ResponseEntity<Movie>(HttpStatus.NO_CONTENT);
	}



	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<Movie> deleteAllUsers() {
		logger.info("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<Movie>(HttpStatus.NO_CONTENT);
	}

}