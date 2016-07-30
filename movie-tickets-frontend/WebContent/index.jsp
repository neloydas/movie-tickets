<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.mtweb.RestClient"%>
<%@ page import="com.mtweb.model.Movie"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Bootstrap's better rendering method for mobile device scaling.-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YATS</title>
</head>

<body background="test.png">

	<div class="jumbotron text-center">
		<h1>Home</h1>
		<p>Welcome!</p>

	</div>

	<ul class="nav nav-pills nav-justified">
		<li class="active"><a href="#">Home</a></li>
		<li><a href="Movies.jsp">Movies</a></li>
		<li><a href="About.jsp">About us</a></li>

	</ul>


	<div class="container">

		<div class="panel-group">
			<div class="panel panel-info">
				<div class="panel-heading">Upcoming movies:</div>

				<table class="table table-inverse">
					<thead>
						<tr>
							<th>Name</th>
							<th>Description</th>
							<th>Release Date</th>
							<th>Genre</th>
							<th>Poster</th>
						</tr>
					</thead>
					<tbody>
	<%
		Movie[] mov = new RestClient().getAllMovielist();
		for (Movie m : mov) {
			out.write("<tr>\n");
			out.write("<th scope=\"row\">" + m.getTitle() + "</th>\n");
			out.write("<td>" + m.getPlot() + "</td>\n");
			out.write("<td>" + m.getYear() + "</td>\n");
			out.write("<td>" + m.getGenre() + "</td>\n");
			out.write("<td><img src=\"" + m.getPoster().toString() + "\" width=\"83\" height=\"119\"></td>\n");
			out.write("</tr>\n");
		} 
	%>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="container">

		<div class="panel-group">
			<div class="panel panel-success">
				<div class="panel-heading">Latest News and Gossip:</div>
				<div class="panel-body">
					<a href="linkhere">IMDB ratings flawed?</a>
				</div>
				<div class="panel-body">
					<a href="linkhere">Barack Obama to star in the next Purge
						movie!</a>
				</div>
				<div class="panel-body">
					<a href="linkhere">Call of Duty movie announced!</a>
				</div>


			</div>

		</div>

	</div>


</body>
</html>