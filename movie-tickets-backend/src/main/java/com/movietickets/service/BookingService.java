package com.movietickets.service;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movietickets.model.BookedSeats;
import com.movietickets.model.Seat;

public class BookingService {
	List<Seat> seatList;
	Seat seat;
	static Connection mySqlCon;
	private PreparedStatement stmt;

	public boolean bookSeatsService(String imdbid, String moviedate, BookedSeats seats) {

		boolean status = false;
		int update = 0;

		try {
			getDatabaseConnection();
			for (int seatNo : seats.getSeats()) {
				String updateStmt = "UPDATE `ticketstore`.`seats` SET `ticketid`=? WHERE `imdbid`=? AND `moviedate`=? AND seatnumber = ?";
				stmt = mySqlCon.prepareStatement(updateStmt);
				stmt.setString(1, imdbid + moviedate + "-" + seatNo);
				stmt.setString(2, imdbid);
				stmt.setString(3, moviedate);
				stmt.setInt(4, seatNo);
				update = stmt.executeUpdate();
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (update > 0) {
			status = true;
		}
		return status;
	}

	
	public List<Seat> getSeatsMap(String imdbid, String moviedate) {

		seatList = new ArrayList<Seat>();
		try {
			getDatabaseConnection();
			stmt = mySqlCon.prepareStatement("SELECT * FROM ticketstore.seats WHERE imdbid = ? AND moviedate = ? AND ticketid != 'NA'");
			stmt.setString(1, imdbid);
			stmt.setString(2, moviedate);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				seat = new Seat();
				seat.setImdbid(rs.getString("imdbid"));
				seat.setMoviedate(rs.getString("moviedate"));
				seat.setSeatnumber(rs.getInt("seatnumber"));
				seat.setTicketid(rs.getString("ticketid"));
				seatList.add(seat);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seatList;
	}
	

	public List<Seat> getTickets(BookedSeats seats) {
		seatList = new ArrayList<Seat>();
		
		try {
			getDatabaseConnection();
			stmt = mySqlCon.prepareStatement("SELECT * FROM ticketstore.seats WHERE imdbid = ? AND moviedate = ? AND seatnumber IN (?)");
			stmt.setString(1, seats.getImdbid());
			stmt.setString(2, seats.getMoviedate());

			Array array = stmt.getConnection().createArrayOf("VARCHAR", seats.getSeats().toArray());
			stmt.setArray(3, array);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				seat = new Seat();
				seat.setImdbid(rs.getString("imdbid"));
				seat.setMoviedate(rs.getString("moviedate"));
				seat.setSeatnumber(rs.getInt("seatnumber"));
				seat.setTicketid(rs.getString("ticketid"));
				seatList.add(seat);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seatList;
	}

	
	
	// private method for getting database connection
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
