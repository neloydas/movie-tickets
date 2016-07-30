package com.mtweb.model;


public class Schedule {
	private String imdbid;
	private String title;
	private String moviedate;
	private double ticketprice;
	private int freeseats;
	
	
	public String getImdbid() {
		return imdbid;
	}
	public void setImdbid(String imdbid) {
		this.imdbid = imdbid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMoviedate() {
		return moviedate;
	}
	public void setMoviedate(String moviedate) {
		this.moviedate = moviedate;
	}
	public double getTicketprice() {
		return ticketprice;
	}
	public void setTicketprice(double ticketprice) {
		this.ticketprice = ticketprice;
	}
	public int getFreeseats() {
		return freeseats;
	}
	public void setFreeseats(int freeseats) {
		this.freeseats = freeseats;
	}

}