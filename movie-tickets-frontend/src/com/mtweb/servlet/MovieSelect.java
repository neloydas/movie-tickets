package com.mtweb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MovieSelect
 */
@WebServlet("/choice")
public class MovieSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieSelect() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/movietickets/Movies.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String movie = request.getParameter("movie");
		String time = request.getParameter("time");
		request.setAttribute("movie", movie);
		request.setAttribute("time", time);
		request.getRequestDispatcher("/SeatSelection.jsp").forward(request, response);
	}

}
