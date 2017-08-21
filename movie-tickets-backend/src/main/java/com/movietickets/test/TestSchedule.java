package com.movietickets.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.movietickets.model.Schedule;
import com.movietickets.service.ScheduleService;



public class TestSchedule {

	static ScheduleService schedul = null;
	static Connection mySqlCon;
	final static String DB = "jdbc:mysql://localhost/";
	final static String USER = "root";
	final static String PSSW = "abcd1234";
	final static String TABLE = "screening";
	static List<Schedule> listOfSchedules = null;
	static Schedule schedule = null;
	
	@BeforeClass
	public static void setUp() throws Exception {
		schedule = new Schedule();
	    schedul = new ScheduleService();
	    listOfSchedules = new ArrayList<Schedule>();

	    Class.forName("com.mysql.jdbc.Driver");
	    mySqlCon = DriverManager.getConnection(DB, USER, PSSW);
	}
	
	@AfterClass
	public static void tearDown() {
	    // Release used resources and do other necessary things in this
	    // method when all tests are finished
		schedul = null;
		schedule = null;
	    listOfSchedules.clear();
	    try {
	        mySqlCon.close();
	        mySqlCon = null;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	@Test
	public void testSchedule() {
		schedule.setImdbid("1");
		schedule.setTitle("GOOD");
		schedule.setMoviedate("2000");
		schedule.setTicketprice(2.5d);
		schedule.setFreeseats(10);
		boolean status = true;
		
		if (!schedule.getImdbid().equals("1")) status = false;
		if (!schedule.getTitle().equals("GOOD")) status = false;
		if (!schedule.getMoviedate().equals("2000")) status = false;
		if (schedule.getTicketprice() != 2.5d ) status = false;
		if (schedule.getFreeseats() != 10) status = false;
		
		assertEquals("Couldn't set and get same Scedule model values", true, status);
	}
	
	@Test
	public void testInsertMovieSchedule() throws Exception {
		boolean status = false;
		
		status = schedul.insertMovieSchedule(schedule);
		
		assertEquals("Couldn't insert choosen schedule", true, status);
		
	}
	
/*	@Test
	public void testGetSingleSchedule() throws Exception { 

	    fillDB();
	    schedule = schedul.getSingleMovieSchedule("1");

	    assertEquals("Couldn't read schedule by id", "1", schedule.getImdbid());
	}*/
	
	@Test
	public void testGetAllSchedules() throws Exception { 
		listOfSchedules = schedul.getAllSchedules();

	    fillDB();

	    assertEquals("Couldn't read all movies", false, listOfSchedules.isEmpty());
	}
	
	@Test
	public void testDeleteSchedule() throws Exception { 
	    boolean status = false;

	    status = schedul.deleteSchedule("1", "2000");

	    assertEquals("Couldn't delete choosen schedule", true, status);
	}
	
	@Test
	public void testUpdateSchedule() throws Exception {
		boolean status = false;
		
		schedule.setTitle("qwerty");
		schedule.setTicketprice(2.9d);
		schedule.setFreeseats(12);
		schedule.setImdbid("1");
		schedule.setMoviedate("2000");
		
		status = schedul.updateSchedule(schedule);
		
		assertEquals("Couldn't update chosen schedule", true, status);
	}
	
	//fill up DB with test data
	private void fillDB() throws SQLException {
	    PreparedStatement stmt;
	    int new_id = countSchedule()+1;
	    String sql = "INSERT INTO `ticketstore`.`screening` VALUES ('"+ new_id +"','Two',"
	    		+ "'2000', 2.5, 10);";
	    stmt = mySqlCon.prepareStatement(sql);
	    stmt.executeUpdate();
	}
	
	private int countSchedule() throws SQLException {
	    PreparedStatement stmt;
	    String sql = "SELECT COUNT(*) FROM `ticketstore`.`screening`";
	    stmt = mySqlCon.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();
	    int count = 0;

	    while (rs.next()) {
	        count = rs.getInt(1);
	    }
	    return count;
	}

}


