package com.mtweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mtweb.model.BookedSeats;
import com.mtweb.model.Movie;
import com.mtweb.model.Schedule;
import com.mtweb.model.Seat;


public class RestClient {
	Client client;
	ObjectMapper mapper;
	
	public Movie[] getAllMovielist(){
		Movie[] mov = new Movie[20];
		client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/mtservice/movies/");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		String json = invocationBuilder.get(String.class);
		mapper = new ObjectMapper();
		try {
			mov = mapper.readValue(json, Movie[].class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 		return mov;
	}
	
	public Movie getSingleMovie(String imdbid){
		Movie movie = new Movie();
		client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/mtservice/movies/" + imdbid);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		String json = invocationBuilder.get(String.class);
		mapper = new ObjectMapper();
		try {
			movie = mapper.readValue(json, Movie.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return movie;
	}
	
	public Schedule[] getMovieSchedule(String imdbid){
		Schedule[] scheduleList = new Schedule[10];
		client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/mtservice/schedules/" + imdbid);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		String json = invocationBuilder.get(String.class);
		mapper = new ObjectMapper();
		try {
			scheduleList = mapper.readValue(json, Schedule[].class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scheduleList;
	}
	
	public String seatsBooking(String title, String time, String[] seats){
		Movie[] mov = new Movie[20];
		String imdbid = new String();
		BookedSeats bs = new BookedSeats();
		List<Integer> list = new ArrayList<>();
		
		mov = getAllMovielist();
		for(Movie m : mov){
			if(m.getTitle().equals(title))
				imdbid = m.getImdbid();
		}
		
		bs.setImdbid(imdbid);
		bs.setMoviedate(time);
		for(String s : seats){
			list.add(Integer.parseInt(s));
		}
		
		bs.setSeats(list);
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/mtservice/screen/book?imdbid=" + imdbid + "&moviedate=" + time);
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.put(Entity.entity(bs, MediaType.APPLICATION_JSON));
		String success = response.readEntity(String.class);
		
		return success;
	}
	

	public Seat[] getBookedSeats(String title, String time){
		Seat[] seats = new Seat[50];
		Movie[] mov = new Movie[20];
		String imdbid = new String();
		mov = getAllMovielist();
		for(Movie m : mov){
			if(m.getTitle().equals(title))
				imdbid = m.getImdbid();
		}
		
		client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/mtservice/screen/seats?imdbid=" + imdbid + "&moviedate=" + time);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		String json = invocationBuilder.get(String.class);
		mapper = new ObjectMapper();
		try {
			seats = mapper.readValue(json, Seat[].class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 		return seats;
	}
	

}
