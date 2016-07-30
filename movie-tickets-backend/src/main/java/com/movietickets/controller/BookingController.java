package com.movietickets.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.movietickets.model.BookedSeats;
import com.movietickets.model.Seat;
import com.movietickets.service.BookingService;


@Path("/screen")
public class BookingController {
	List<Seat> seatList = null;
	boolean status;

	// FIXME Need to figure out how to pass data from webpage to webservice
	// call...POST??

	@PUT
	@Path("/book")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String bookSeats(@QueryParam("imdbid") String imdbid, @QueryParam("moviedate") String moviedate,
			BookedSeats seats) {

		status = new BookingService().bookSeatsService(imdbid, moviedate, seats);
		if (status == true)
			return "true";
		else
			return "false";
	}

	
	@GET
	@Path("/seats")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Seat> getAllSeats(@QueryParam("imdbid") String imdbid, @QueryParam("moviedate") String moviedate){
		return new BookingService().getSeatsMap(imdbid, moviedate);
	}
	
	
	//TODO Need to call this method from BookingDone.jsp
	//FIXME Cannot pass seats in GET method - Reservation cannot be shown because there is no provision in the table
/*	
	@GET
	@Path("/reservation")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Seat> getSeatids(){
		return new BookingService().getTickets();
	}
	*/
}