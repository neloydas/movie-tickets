<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.mtweb.RestClient"%>
<%@ page import="com.mtweb.model.Movie"%>
<%@ page import="com.mtweb.model.Schedule"%>
<%@ page import="java.util.TimeZone"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>


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
<style type="text/css">
button {
    background:#73C6B6!important;
     border:none; 
     padding:0!important;
    
    /*optional*/
    font-family:arial,sans-serif; /*input has OS specific font-family*/
     color:#17202A;
     text-decoration:underline;
     cursor:pointer;
}
</style>
</head>

<body>

<div class = "jumbotron text-center">
<h1> Movies </h1>
<p> Checkout the current movies that are available to view! </p>

</div>

<ul class ="nav nav-pills nav-justified">
<li><a href="index.jsp">Home</a></li>
<li class="active"><a href ="#">Movies</a></li>
<li><a href="About.jsp">About us</a></li>
</ul>

<table class="table table-inverse">
<thead>
<tr>
<th> Name </th>
<th> Description </th>
<th> Runtime </th>
<th> Genre </th>
<th> Director </th>
<th> Actors </th>
<th>Timings</th>
<th> Poster </th>
</tr>
</thead>
<tbody>

	<%
		Movie[] mov = new RestClient().getAllMovielist();

		for (Movie m : mov) {
			out.write("<tr>\n");
			out.write("<th scope=\"row\">" + m.getTitle() + "</th>\n");
			out.write("<td>" + m.getPlot() + "</td>\n");
			out.write("<td>" + m.getRuntime() + "</td>\n");
			out.write("<td>"+ m.getGenre() + "</td>\n");
			out.write("<td>"+ m.getDirector() + "</td>\n");
			out.write("<td>"+ m.getActors() + "</td>\n");
			out.write("<td>\n");
			Schedule[] scheduleList = new RestClient().getMovieSchedule(m.getImdbid());
			for(Schedule e : scheduleList){
				Date date = new Date(Long.parseLong(e.getMoviedate())*1000L);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM-HH:mm");
				sdf.setTimeZone(TimeZone.getDefault());
				String formattedDate = sdf.format(date);
				out.println("<form action=\"/movietickets/choice\" method=\"post\">");
				out.println("<input type=\"hidden\" name=\"time\" value=\"" + e.getMoviedate() + "\"/>");
				out.println("<input type=\"hidden\" name=\"movie\" value=\"" + e.getTitle() + "\" />");
				out.println("<button type=\"submit\" class=\"btn btn-link\">" + formattedDate + "</button>");
				out.println("</form>");
			}
			out.write("</td>\n");
			out.write("<td><img src=\"" + m.getPoster().toString() + "\" width=\"83\" height=\"119\"></td>\n");
			out.write("</tr>\n");
		}
	%>

</tbody>
</table>

</body>
</html>