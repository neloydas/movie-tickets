package com.movietickets.service;

import java.sql.CallableStatement;
/*
 * Author Neloy Das
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movietickets.model.Schedule;

public class ScheduleService {
	private List<Schedule> listOfSchedules;
	private static Connection mySqlCon;
	private Schedule schedule;
	private PreparedStatement stmt;
	private CallableStatement calstmt;

	public List<Schedule> getAllSchedules() {

		listOfSchedules = new ArrayList<Schedule>();
		try {
			getDatabaseConnection();
			stmt = mySqlCon.prepareStatement("SELECT * FROM ticketStore.screening");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				schedule = new Schedule();
				schedule.setImdbid(rs.getString("imdbid"));
				schedule.setTitle(rs.getString("title"));
				schedule.setMoviedate(rs.getString("moviedate"));
				schedule.setTicketprice(rs.getDouble("ticketprice"));
				schedule.setFreeseats(rs.getInt("freeseats"));
				listOfSchedules.add(schedule);
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfSchedules;
	}

	public List<Schedule> getSingleMovieSchedule(String id) {
		
		listOfSchedules = new ArrayList<Schedule>();

		try {
			getDatabaseConnection();
			stmt = mySqlCon.prepareStatement("SELECT * FROM ticketStore.screening where imdbid = ?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				schedule = new Schedule();
				schedule.setImdbid(rs.getString("imdbid"));
				schedule.setTitle(rs.getString("title"));
				schedule.setMoviedate(rs.getString("moviedate"));
				schedule.setTicketprice(rs.getDouble("ticketprice"));
				schedule.setFreeseats(rs.getInt("freeseats"));
				listOfSchedules.add(schedule);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfSchedules;
	}

	public Boolean insertMovieSchedule(Schedule schedule) {
		int err_cd = 1;
		String out_msg = "";

		try {
			getDatabaseConnection();
			String spCall = "{call ticketstore.insertScreening(?,?,?,?,?,?)}";
			calstmt = mySqlCon.prepareCall(spCall);
			calstmt.setString(1, schedule.getImdbid());
			calstmt.setString(2, schedule.getTitle());
			calstmt.setString(3, schedule.getMoviedate());
			calstmt.setDouble(4, schedule.getTicketprice());
			calstmt.registerOutParameter(5, java.sql.Types.INTEGER);
			calstmt.registerOutParameter(6, java.sql.Types.VARCHAR);

			calstmt.executeUpdate();

			err_cd = calstmt.getInt(5);
			out_msg = calstmt.getString(6);
			if (err_cd != 0)
				throw new SQLException(out_msg);

			calstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		if (err_cd == 0) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateSchedule(Schedule schedule) {
		boolean status = false;
		int update = 0;

		try {
			getDatabaseConnection();
			String updateStmt = "UPDATE ticketstore.screening SET title = ?, ticketprice = ?, "
					+ "freeseats = ? WHERE imdbid = ? and moviedate = ?";

			stmt = mySqlCon.prepareStatement(updateStmt);
			stmt.setString(1, schedule.getTitle());
			stmt.setDouble(2, schedule.getTicketprice());
			stmt.setInt(3, schedule.getFreeseats());
			stmt.setString(4, schedule.getImdbid());
			stmt.setString(5, schedule.getMoviedate());
			update = stmt.executeUpdate();

			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (update > 0) {
			status = true;
		}

		return status;
	}

	public Boolean deleteSchedule(String imdbid, String moviedate) {
		int update = 0;

		String sql = "DELETE FROM ticketStore.screening WHERE imdbid = ? AND moviedate = ?";
		getDatabaseConnection();

		try {
			stmt = mySqlCon.prepareStatement(sql);
			stmt.setString(1, imdbid);
			stmt.setString(2, moviedate);
			update = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (update > 0)
			return true;
		else
			return false;
	}

	// Neloy - private method for getting database connection
	private static void getDatabaseConnection() {
		try {
			if (mySqlCon == null) {
				Class.forName("com.mysql.jdbc.Driver");
				mySqlCon = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "abcd1234");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
