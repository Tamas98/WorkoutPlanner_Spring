<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Workout Planner</title>
<style>
	#header{
		margin:0;
		color:white;
		background-color:black;
		width:100%;
	}
	
	a{
		text-decoration: none;
	}
	
	a:visited,a:link{
		color:white;
	}
	
	a:hover{
		color: red;
		font-size: 1.7rem;
		font-weight:500;
		transition: 0.5s ease-in-out;
	}
	
	a:active{
		color:red;
		font-weight:bold;
	}
	
	body{
		margin:0;
	}
	
	#nav-buttons{
		display:flex;
		justify-content:flex-end;
		color:white;
		list-style:none;
		padding:2rem;
	}
	
	.nav-button{
		font-size:1.5rem;
		margin:0 20px;
	}
	
</style>
</head>
<body>
	<div id="header">
		<ul id="nav-buttons">
			<li class="nav-button">
				<a href="/">Home</a>
			</li>
			<li class="nav-button">
				<a href="meals">Meals</a>
			</li>
			<li class="nav-button">
				<a href="exercises">Exercises</a>
			</li>
			<li class="nav-button">
				<a href="eval">Evaluations</a>
			</li>
			<li class="nav-button">
				<a href="about">About</a>
			</li>
		</ul>
	</div>
</body>
</html>