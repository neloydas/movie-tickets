<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import= "com.mtweb.servlet.SeatMap" %>
    <%@ page import= "com.mtweb.RestClient" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Bootstrap's better rendering method for mobile device scaling.-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YATS</title>
</head>


<body background="test.png">
<%
String[] seats = request.getParameterValues("seat");
int amountOfSeats = seats.length;
%>
 

<div class = "jumbotron text-center">
<h1> Reservation Confirmed </h1>
<p> Your reservation information is located below.</p>
<br>
<p> Thank you. </p>

</div>

<ul class ="nav nav-pills nav-justified">
<li><a href="index.jsp">Home</a></li>
<li><a href="Movies.jsp">Movies</a></li>
<li class="active"><a href ="#">Bookings</a></li>
<li><a href="About.jsp">About us</a></li>
</ul>

<br>
<br>

<div class = "jumbotron text-center">
<center><h3> Reserved Seat(s): </h3></center>
<center><h2>
<% for(int i = 0; i < amountOfSeats; i ++){ 
	out.println(seats[i]);
	if(i != amountOfSeats-1)
		out.print(", ");
	}
%>
	</h2></center>
</div>
<div class = "jumbotron text-center">
<center><h3> Reservation Number(s): </h3></center>
<center><h2>
<% 
for(int i = 0; i < amountOfSeats; i ++){
	out.println(seats[i] + "\n"); 
	} 
%>
</h2></center>
</div>

<div class = "jumbotron text-center">
<center><h3> Total Cost:  </h3></center>
<center><h2>
<% 
double cost = amountOfSeats * 7.95;
out.println(cost); 
%>
</h2></center>
</div>

</body>
</html>