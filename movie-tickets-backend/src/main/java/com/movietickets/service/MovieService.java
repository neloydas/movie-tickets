package com.movietickets.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movietickets.model.Movie;

public class MovieService {
	static Connection mySqlCon;
	private Movie movie;
	private PreparedStatement stmt;
	private List<Movie> movieList;

	public List<Movie> getAllMovies() throws ClassNotFoundException {

		movieList = new ArrayList<Movie>();
		try {
			getDatabaseConnection();
			stmt = mySqlCon.prepareStatement("SELECT * FROM ticketStore.movielist");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				movie = new Movie();
				movie.setImdbid(rs.getString("imdbid"));
				movie.setTitle(rs.getString("title"));
				movie.setGenre(rs.getString("genre"));
				movie.setPlot(rs.getString("plot"));
				movie.setPoster(rs.getString("poster"));
				movie.setRuntime(rs.getString("runtime"));
				movie.setDirector(rs.getString("director"));
				movie.setActors(rs.getString("actors"));
				movie.setYear(rs.getInt("year"));
				movie.setDefaultprice(rs.getDouble("defaultprice"));
				movieList.add(movie);
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movieList;
	}

	public Movie getSingleMovie(String id) {
		movie = new Movie();

		try {
			getDatabaseConnection();
			stmt = mySqlCon.prepareStatement("SELECT * FROM ticketStore.movielist where imdbid = ?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				movie.setImdbid(rs.getString("imdbid"));
				movie.setTitle(rs.getString("title"));
				movie.setGenre(rs.getString("genre"));
				movie.setPlot(rs.getString("plot"));
				movie.setPoster(rs.getString("poster"));
				movie.setRuntime(rs.getString("runtime"));
				movie.setDirector(rs.getString("director"));
				movie.setActors(rs.getString("actors"));
				movie.setYear(rs.getInt("year"));
				movie.setDefaultprice(rs.getDouble("defaultprice"));
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movie;
	}
	
	
	
	// update choosen movie by Girts
	public boolean updateMovie(Movie movie) throws Exception {
		boolean status = false;
		int update = 0;
		
		//Input validation check
		if(movie.getRuntime() == null || "".equals(movie.getRuntime()) || Integer.parseInt(movie.getRuntime()) == 0)
			return false;
		if(movie.getTitle() == null || "".equals(movie.getTitle()))
			return false;
		
		
		try {
			getDatabaseConnection();
			String updateStmt = "UPDATE ticketStore.movielist SET title = ?, genre = ?, plot = ?, "
					+ "poster = ?, runtime = ?, director = ?, actors = ?, year = ?, defaultprice = ? WHERE imdbid = ?";

			stmt = mySqlCon.prepareStatement(updateStmt);
			stmt.setString(1, movie.getTitle());
			stmt.setString(2, movie.getGenre());
			stmt.setString(3, movie.getPlot());
			stmt.setString(4, movie.getPoster());
			stmt.setString(5, movie.getRuntime());
			stmt.setString(6, movie.getDirector());
			stmt.setString(7, movie.getActors());
			stmt.setInt(8, movie.getYear());
			stmt.setDouble(9, movie.getDefaultprice());
			stmt.setString(10, movie.getImdbid());

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


	// Girts - deletes movie by object movie
	public boolean deleteMovie(String imdbid) throws Exception {
		int update = 0;

		String sql = "DELETE FROM ticketStore.movielist WHERE imdbid = ?";
		getDatabaseConnection();

		stmt = mySqlCon.prepareStatement(sql);
		stmt.setString(1, imdbid);

		update = stmt.executeUpdate();

		if (update > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Insert movie method by Undine
	public boolean insertMovie(Movie movie) throws Exception {
		
		int insert = 0;
		
		//Check if the imdbid is duplicate entry. So no database primary key violation happen
		List<Movie> movList = getAllMovies();
		for(Movie m: movList){
			if(m.getImdbid() == movie.getImdbid())
				return false;
		}
		//check if the movie actually have a runtime value
		if(movie.getRuntime() == null || "".equals(movie.getRuntime()) || Integer.parseInt(movie.getRuntime()) == 0)
			return false;
		
		try {
			getDatabaseConnection();
			String insertStmt = "INSERT INTO ticketStore.movielist VALUE(?,?,?,?,?,?,?,?,?,?)";

			stmt = mySqlCon.prepareStatement(insertStmt);
			stmt.setString(1, movie.getImdbid());
			stmt.setString(2, movie.getTitle());
			stmt.setString(3, movie.getGenre());
			stmt.setString(4, movie.getPlot());
			stmt.setString(5, movie.getPoster());
			stmt.setString(6, movie.getRuntime());
			stmt.setString(7, movie.getDirector());
			stmt.setString(8, movie.getActors());
			stmt.setInt(9, movie.getYear());
			stmt.setDouble(10, movie.getDefaultprice());

			insert = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (insert > 0) {
			return true;
		} else {
			return false;
		}
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