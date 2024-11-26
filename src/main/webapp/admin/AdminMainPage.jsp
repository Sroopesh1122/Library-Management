<%@page import="com.library.dao.CategoryDaoImpl"%>
<%@page import="com.library.dao.AuthorDaoImpl"%>
<%@page import="com.library.dao.BookDaoImpl"%>
<%@page import="com.library.dao.BookDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>
<%request.setAttribute("menu", "Dashboard"); %>
</head>
<style>
body {
	min-height: 100vh;
	width: 100%;
	font-family: "Outfit", sans-serif;
}

.dashboard {
	min-height: 60vh;
	padding: 30px; width : 100%;
	display: flex;
	flex-wrap: wrap;
	justify-content: flex-start;
	align-items: flex-start;
	width: 100%;
}

.dashboard .card {
	width: 250px;
	margin:15px;
	border: 1px solid gray;
	padding: 10px;
	border-radius: 10px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	cursor: pointer;
	text-decoration: none;
	color: black;
	
}

.dashboard .card:hover {
	box-shadow: 2px 3px 6px gray  , -2px -3px 6px gray !important;
}

.dashboard .card .card-img {
	width: 200px;
	height: 150px;
}
.dashboard .card .item-count{
  font-size: 1.2rem;
}
</style>

<body>

	<%@include file="/admin/NavBar.jsp"%>
	<% 
	
	 int totalBooks = new BookDaoImpl().getTotalBooks(); 
	 int totalAuthors = new AuthorDaoImpl().getTotal();
	 int totalCategories = new CategoryDaoImpl().getTotalCategories(null);
	 
	%>


	<%-- Main Section --%>

	<section class="dashboard">

		<a class="card" href="<%= request.getContextPath()+"/books/getAllBooks"%>">

			<img alt="Books"
				src="https://www.transparentpng.com/thumb/book/bekZXl-book-free-download.png"
				class="card-img">
			<h2 class="card-title">Books</h2>
			<span class="item-count"><%=totalBooks %></span>
		</a>
		<a class="card" href="<%= request.getContextPath()+"/authors/Authors.jsp"%>">

			<img alt="authors"
				src="https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg"
				class="card-img">
			<h2 class="card-title">Authors</h2>
			<span class="item-count"><%=totalAuthors %></span>
		</a>
		
         
         <a class="card" href="<%= request.getContextPath()+"/categories/Categories.jsp"%>">

			<img alt="categories"
				src="https://img.freepik.com/premium-photo/inside-modern-library-with-white-book-shelves-white-wall_662214-215898.jpg"
				class="card-img">
			<h2 class="card-title">Categories</h2>
			<span class="item-count"><%=totalCategories %></span>
		</a>

	</section>


</body>
</html>