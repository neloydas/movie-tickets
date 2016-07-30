package com.movietickets.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.movietickets.model.Schedule;
import com.movietickets.service.ScheduleService;


@Path("/schedules")
public class ScheduleController {
	
	//Schedule schedule;
	List<Schedule> listOfSchedules;
	Boolean status;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Schedule> getAllSchedules(){
		listOfSchedules = new ScheduleService().getAllSchedules();
		return listOfSchedules;
	}
	
	@GET
	@Path("/{imdbid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Schedule> getSingleSchedule(@PathParam("imdbid") String id) {
		listOfSchedules = new ScheduleService().getSingleMovieSchedule(id);
		return listOfSchedules;
	}
	
	
	/*
	 * Add screening method will also create 50 seats per screening in the seats table. 
	 */
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertSchedule(Schedule schedule){
		
		try {
			status = new ScheduleService().insertMovieSchedule(schedule);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (status == true)
			return "true";
		else
			return "false";
	}

	/*
	 * Nobody calls this method from front-end - hence deprecated
	 * 
	@PUT
	@Path("/update")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateSchedule(@QueryParam("imdbid") String imdbid, @QueryParam("moviedate") String moviedate, Schedule schedule) {
		schedule.setImdbid(imdbid);
		schedule.setMoviedate(moviedate);
		try {
			status = new ScheduleService().updateSchedule(schedule);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (status == true)
			return "true";
		else
			return "false";
	}
*/
		
	@DELETE
	@Path("/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteSchedule(@QueryParam("imdbid") String imdbid, @QueryParam("moviedate") String moviedate) {

		try {
			status = new ScheduleService().deleteSchedule(imdbid, moviedate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (status == true)
			return "true";
		else
			return "false";
	}

}
