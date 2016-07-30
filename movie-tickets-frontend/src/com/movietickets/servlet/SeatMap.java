package com.movietickets.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movietickets.RestClient;


/**
 * Servlet implementation class SeatMap
 */
@WebServlet("/seatmap")
public class SeatMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String[] seats;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeatMap() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/TestWebApp/Movies.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		seats = request.getParameterValues("seat");
		String success = new RestClient().seatsBooking(request.getParameter("movie"), request.getParameter("time"),	seats);
		if (success.equals("true"))
			request.getRequestDispatcher("/BookingDone.jsp").forward(request, response);
		else
			response.getWriter().write("Internal Server Error. Your booking failed. Please try again!");
	}

}
