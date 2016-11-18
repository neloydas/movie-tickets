package com.movietickets.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.movietickets.model.Movie;
import com.movietickets.service.MovieService;


@Path("/movies")
public class MovieController {
	List<Movie> movieList;
	boolean status;
	Movie movie;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAllMovies() {
		movieList = new ArrayList<>();
		try {
			movieList = new MovieService().getAllMovies();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return movieList;
	}

	@GET
	@Path("/{imdbid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Movie getMovie(@PathParam("imdbid") String id) {
		movie = new MovieService().getSingleMovie(id);
		return movie;
	}
	
	
	// edited by Girts for movie update and list printing for testing
	@PUT
	@Path("/update/{imdbid}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateMovie(@PathParam("imdbid") String id, Movie movie) {
		movie.setImdbid(id);
		try {
			status = new MovieService().updateMovie(movie);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (status == true)
			return "true";
		else
			return "false";
	}

	
	@DELETE
	@Path("/delete/{imdbid}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteMovie(@PathParam("imdbid") String imdbid) {

		try {
			status = new MovieService().deleteMovie(imdbid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (status == true)
			return "true";
		else
			return "false";
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertMovie(Movie movie){
		
		try {
			status = new MovieService().insertMovie(movie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (status == true)
			return "true";
		else
			return "false";
	}
	
	
	
}