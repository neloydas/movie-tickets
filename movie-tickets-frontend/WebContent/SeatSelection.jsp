<%@page import="com.movietickets.model.BookedSeats"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="com.movietickets.RestClient"%>
<%@ page import="com.movietickets.model.Movie"%>
<%@ page import="com.movietickets.model.Schedule"%>
<%@ page import="java.util.TimeZone"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.movietickets.model.Seat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Bootstrap's better rendering method for mobile device scaling.-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="css/chk_button.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YATS</title>

<script src="http://s.codepen.io/assets/libs/modernizr.js" type="text/javascript"></script>
<meta name="viewport" content="width=device-width">

<link rel="stylesheet" href="//codepen.io/assets/reset/normalize.css">
<style>

, :before, *:after {
box-sizing: border-box;
}

html {
font-size: 16px;
}

.plane {
margin: 100px 200px;
max-width: 500px;
}

.exit {
position: relative;
height: 50px;
}
.exit:before, .exit:after {
content: "EXIT";
font-size: 14px;
line-height: 18px;
padding: 0px 2px;
font-family: "Arial Narrow", Arial, sans-serif;
display: block;
position: absolute;
background: green;
color: white;
top: 50%;
transform: translate(0, -50%);
}

ol {
list-style: none;
padding: 0;
margin: 0;
}

.seats {
display: flex;
flex-direction: row;
flex-wrap: nowrap;
justify-content: flex-start;
}

.seat {
display: flex;
flex: 0 0 0;
padding: 5px;
position: relative;
}
.seat:nth-child(5) {
margin-right: 0;
}
.seat input[type=checkbox] {
position: absolute;
opacity: 0;
}
.seat input[type=checkbox]:checked + label {
background: #bada55;
-webkit-animation-name: rubberBand;
animation-name: rubberBand;
animation-duration: 300ms;
animation-fill-mode: both;
}
.seat input[type=checkbox]:disabled + label {
background: #dddddd;
text-indent: -9999px;
overflow: hidden;
}
.seat input[type=checkbox]:disabled + label:after {
content: "X";
text-indent: 0;
position: absolute;
top: 4px;
left: 50%;
transform: translate(-50%, 0%);
}
.seat input[type=checkbox]:disabled + label:hover {
box-shadow: none;
cursor: not-allowed;
}
.seat label {
display: block;
position: relative;
width: 100%;
text-align: center;
font-size: 14px;
font-weight: bold;
line-height: 1.5rem;
padding: 4px 0;
background: #F42536;
border-radius: 5px;
animation-duration: 300ms;
animation-fill-mode: both;
}
.seat label:before {
content: "";
position: absolute;
width: 75%;
height: 75%;
top: 1px;
left: 50%;
transform: translate(-50%, 0%);
background: rgba(255, 255, 255, 0.4);
border-radius: 3px;
}
.seat label:hover {
cursor: pointer;
box-shadow: 0 0 0px 2px #5C6AFF;
}

@-webkit-keyframes rubberBand {
0% {
-webkit-transform: scale3d(1, 1, 1);
transform: scale3d(1, 1, 1);
}
30% {
-webkit-transform: scale3d(1.25, 0.75, 1);
transform: scale3d(1.25, 0.75, 1);
}
40% {
-webkit-transform: scale3d(0.75, 1.25, 1);
transform: scale3d(0.75, 1.25, 1);
}
50% {
-webkit-transform: scale3d(1.15, 0.85, 1);
transform: scale3d(1.15, 0.85, 1);
}
65% {
-webkit-transform: scale3d(0.95, 1.05, 1);
transform: scale3d(0.95, 1.05, 1);
}
75% {
-webkit-transform: scale3d(1.05, 0.95, 1);
transform: scale3d(1.05, 0.95, 1);
}
100% {
-webkit-transform: scale3d(1, 1, 1);
transform: scale3d(1, 1, 1);
}
}
@keyframes rubberBand {
0% {
-webkit-transform: scale3d(1, 1, 1);
transform: scale3d(1, 1, 1);
}
30% {
-webkit-transform: scale3d(1.25, 0.75, 1);
transform: scale3d(1.25, 0.75, 1);
}
40% {
-webkit-transform: scale3d(0.75, 1.25, 1);
transform: scale3d(0.75, 1.25, 1);
}
50% {
-webkit-transform: scale3d(1.15, 0.85, 1);
transform: scale3d(1.15, 0.85, 1);
}
65% {
-webkit-transform: scale3d(0.95, 1.05, 1);
transform: scale3d(0.95, 1.05, 1);
}
75% {
-webkit-transform: scale3d(1.05, 0.95, 1);
transform: scale3d(1.05, 0.95, 1);
}
100% {
-webkit-transform: scale3d(1, 1, 1);
transform: scale3d(1, 1, 1);
}
}
.rubberBand {
-webkit-animation-name: rubberBand;
animation-name: rubberBand;
}

</style>

<script type="text/javascript">
function validateForm()
{
    var checkboxs=document.getElementsByName("seat");
    var okay=false;
    for(var i=0,l=checkboxs.length;i<l;i++)
    {
        if(checkboxs[i].checked)
        {
            okay=true;
            break;
        }
    }
    if(okay) document.getElementById("seatForm").submit();
    else alert("Please select atleast one seat first!");
}
</script>

</head>



<body background="test.png">

<div class = "jumbotron text-center">
<h1> Booking - In Progress </h1>
<p> Please enter your details below. </p>

</div>

<ul class ="nav nav-pills nav-justified">
<li><a href="index.jsp">Home</a></li>
<li><a href="Movies.jsp">Movies</a></li>
<li class="active"><a href ="#">Bookings</a></li>
<li><a href="About.jsp">About us</a></li>

</ul>

<div class = "jumbotron text-center">
<h3> Please pick a seat. </h3>
<p> There are 50 seats and 5 rows to choose from for your enjoyment. </p>

</div>


<!-- Move rows, show 2 rows max for now. Each row has 5 movies. -->

<form action="/TestWebApp/seatmap" method="post" id="seatForm">

<center><h5>Movie: <% String movie = request.getParameter("movie");

out.print(movie); %>
</h5></center>
<center><h5>Timing: <% 
String time = request.getParameter("time");
Date date = new Date(Long.parseLong(time)*1000L);
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM-HH:mm");
sdf.setTimeZone(TimeZone.getDefault());
String formattedDate = sdf.format(date);
out.print(formattedDate);
%></h5></center>
<%
out.println("<input type=\"hidden\" name=\"time\" value=\"" + time + "\" />");
out.println("<input type=\"hidden\" name=\"movie\" value=\"" + movie + "\" />"); 
%>
<center> <button type="button" class="btn btn-success" onclick="validateForm()">Get ticket</button></center>
<br>
<button type="button" class="btn btn-default btn-block">SCREEN</button>
<% 

%>
<table class="table table-inverse">
<tbody>
<!-- Seating rows.-->
  <%
  Seat[] seats = new Seat[50];
  seats = new RestClient().getBookedSeats(movie, time);
  int[] a = new int[50];
  boolean match = false;
  
  for(int x = 0 ; x<seats.length ; x++){
	  a[x] = seats[x].getSeatnumber();
  }
  
  for(int i = 1; i<=50 ; i++){
	  if(i==1 || i==11 || i==21 || i==31 || i==41){
		  out.println("<tr>");
	  out.println("<li class=\"row row--" + i + "\">");
	  out.println("<ol class=\"seats\">");
	  }
	out.println("<th>");
	out.println("<li class=\"seat\">");
	for(int y : a){
		if(i == y)
			match = true;
	}
	if(match){
		out.println("<input type=\"checkbox\" disabled name=\"seat\" id=\""+i+"\" value=\""+i+"\" />");
		out.println("<label for=\""+i+"\">"+i+"</label>");
		match = false;
	}else{
		out.println("<input type=\"checkbox\" name=\"seat\" id=\""+i+"\" value=\""+i+"\" />");
		out.println("<label for=\""+i+"\">"+i+"</label>");
	}
		out.println("</li>");
		out.println("</th>");

	if(i==10 || i==20 || i==30 || i==40){
		out.println("</ol>");
		out.println("</li>");
		out.println("</tr>");
	}
  }
  
  %>

</tbody>
</table>
</form>

</body>
</html>