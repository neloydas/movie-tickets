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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.movietickets.model.Movie;
import com.movietickets.service.MovieService;


public class MovieUnitTest {
	static MovieService mov = null;
	static Connection mySqlCon;
	final static String DB = "jdbc:mysql://localhost/";
	final static String USER = "root";
	final static String PSSW = "abcd1234";
	final static String TABLE = "moviesList";
	static List<Movie> movieList = null;
	static Movie movie = null;

	@BeforeClass
	public static void setUp() throws Exception {
		movie = new Movie();
		mov = new MovieService();
		movieList = new ArrayList<Movie>();
		
		Class.forName("com.mysql.jdbc.Driver");
		mySqlCon = DriverManager.getConnection(DB, USER, PSSW);
		
	}
	
	@Before
	public void beforeTest() throws Exception {
		
		fillDB();
	}

	@AfterClass
	public static void tearDown() throws SQLException {
		// Release used resources and do other necessary things in this
		// method when all tests are finished
		mov = null;
		movie = null;
		movieList.clear();
		deleteTestData();
		try {
			mySqlCon.close();
			mySqlCon = null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testModel() throws Exception {
		movie.setImdbid("1");
		movie.setTitle("test movie");
		movie.setPlot("plot");
		movie.setGenre("genre");
		movie.setActors("actors");
		movie.setDirector("director");
		movie.setPoster("poster");
		movie.setRuntime("30");
		movie.setYear(2015);
		movie.setDefaultprice(1.5d);
		boolean status = true;

		if (!movie.getActors().equals("actors"))
			status = false;
		if (movie.getDefaultprice() != 1.5d)
			status = false;
		if (!movie.getDirector().equals("director"))
			status = false;
		if (!movie.getGenre().equals("genre"))
			status = false;
		if (!movie.getImdbid().equals("1"))
			status = false;
		if (!movie.getPlot().equals("plot"))
			status = false;
		if (!movie.getPoster().equals("poster"))
			status = false;
		if (!movie.getRuntime().equals("30"))
			status = false;
		if (!movie.getTitle().equals("test movie"))
			status = false;
		if (movie.getYear() != 2015)
			status = false;

		assertEquals("Couldn't set and get same movie model values", true, status);
	}
	
	@Test 
	public void testInsertMovie() throws Exception {
		
		int new_id = countMovies() + 1;
		
		movie.setImdbid(""+existsID(new_id)+""); 
		movie.setTitle("Other test movie");
		movie.setPlot("plot");
		movie.setGenre("genre");
		movie.setActors("actors");
		movie.setDirector("director");
		movie.setPoster("poster");
		movie.setRuntime("30");
		movie.setYear(2015);
		movie.setDefaultprice(1.5d);
		
		assertEquals("Couldn't insert movie into database", true, mov.insertMovie(movie));
	}
	
	@Test
	public void testGetAllMovies() throws Exception {
		movieList = mov.getAllMovies();

		assertEquals("Couldn't read all movies from database", false, movieList.isEmpty());
	}

	@Test
	public void testGetSingleMovie() throws Exception {
		boolean status = false;
		
		findTestMovie(); 
		movie = mov.getSingleMovie(movie.getImdbid()); 
		
		if (movie.getTitle().contains("test")) {
			status = true;
		}

		assertEquals("Couldn't read movie by id from database", true, status);
	}

	@Test
	public void testUpdateMovie() throws Exception {
		boolean status = false;
		
		findTestMovie();
		
		movie.setTitle("Nice test movie");
		movie.setPlot("plot");
		movie.setGenre("genre");
		movie.setActors("actors");
		movie.setDirector("director");
		movie.setPoster("poster");
		movie.setRuntime("30");
		movie.setYear(2015);
		movie.setDefaultprice(1.5d);
		
		status = mov.updateMovie(movie);

		assertEquals("Couldn't update choosen movie", true, status);
	}
	
	@Test
	public void testDeleteMovie() throws Exception {
		boolean status = false;
		
		findTestMovie();
		status = mov.deleteMovie( "" + movie.getImdbid() + "");

		assertEquals("Couldn't delete choosen movie", true, status);
	}
	
	// fill up DB with test data
	// works if DB has at least one filled row
	static private void fillDB() throws SQLException {
		PreparedStatement stmt;
		int new_id = countMovies() + 1;
		
		String sql = "INSERT INTO ticketstore.movielist VALUES ('"+existsID(new_id)+"','test movie','action', 'yay', 'poster','runtime', 'director', 'actors', 2015, 1.5);";
		stmt = mySqlCon.prepareStatement(sql);
		stmt.executeUpdate();
	}

	// get DB movie table item count
	private static int countMovies() throws SQLException {
		PreparedStatement stmt;
		String sql = "SELECT COUNT(*) FROM ticketstore.movielist";
		stmt = mySqlCon.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		int count = 0;

		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	// frees DB movie table from test files
	private static boolean deleteTestData() throws SQLException {
		PreparedStatement stmt;
		int status = 0;
		String sql = "DELETE FROM ticketstore.movielist WHERE title LIKE '%test%'";
		stmt = mySqlCon.prepareStatement(sql);
		status = stmt.executeUpdate();
		if (status > 0) {
			return true;
		}
		return false;
	}
	
	// get movie test data from DB
	private static void findTestMovie() {
		PreparedStatement stmt;

		try {
			stmt = mySqlCon.prepareStatement("SELECT * FROM ticketstore.movielist where title LIKE '%test%'");
			//stmt.setString(1, id);
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
				
				break; //because we need only one test movie
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return;
	}
	
	//finds out if new id already exists
	private static boolean searchID(String id) throws SQLException {
		PreparedStatement stmt;
		String sql = "SELECT * FROM ticketstore.movielist where imdbid = ?";
		
		stmt = mySqlCon.prepareStatement(sql);
		stmt.setString(1, id);
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	//makes different new id
	private static String existsID(int id) throws SQLException {
		
		while (true) {
			if (searchID(""+id+"")) {
				id++;
			} else {
				break;
			}
		}
		
		return ""+id+"";
	}
}
