package com.example.mongo.rest.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.example.mongo.rest.model.Movie;
import org.springframework.stereotype.Service;


@Service("userService")
public class MovieRepositoryImpl implements MongoRepository<Movie, String>{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Movie> movies;
	
	static{
		movies = populateDummyUsers();
	}

	public List<Movie> findAllUsers() {
		return movies;
	}
	
	public Movie findById(long id) {
		for(Movie movie : movies){
			if(movie.getId() == id){
				return movie;
			}
		}
		return null;
	}
	
	public Movie findByName(String name) {
		for(Movie movie : movies){
			if(movie.getName().equalsIgnoreCase(name)){
				return movie;
			}
		}
		return null;
	}
	
	public void saveUser(Movie movie) {
		movie.setId(counter.incrementAndGet());
		movies.add(movie);
	}

	public void updateMovie(Movie movie) {
		int index = movies.indexOf(movie);
		movies.set(index, movie);
	}

	public void deleteMovieById(long id) {
		
		for (Iterator<Movie> iterator = movies.iterator(); iterator.hasNext(); ) {
		    Movie movie = iterator.next();
		    if (movie.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(Movie movie) {
		return findByName(movie.getName())!=null;
	}
	
	public void deleteAllUsers(){
		movies.clear();
	}

	private static List<Movie> populateDummyUsers(){
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(new Movie(counter.incrementAndGet(),"Sam",30, 70000));
		movies.add(new Movie(counter.incrementAndGet(),"Tom",40, 50000));
		movies.add(new Movie(counter.incrementAndGet(),"Jerome",45, 30000));
		movies.add(new Movie(counter.incrementAndGet(),"Silvia",50, 40000));
		return movies;
	}

}
