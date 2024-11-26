<%@page import="com.library.dao.CategoryDaoImpl"%>
<%@page import="com.library.dao.BookDaoImpl"%>
<%@page import="com.library.dao.BookDao"%>
<%@page import="com.library.dao.CategoryDao"%>
<%@page import="com.library.dto.Category"%>
<%@page import="com.library.dto.Book"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Library Books</title>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>
</head>
<style>
body {
	min-height: 100vh;
	width: 100%;
	font-family: "Outfit", sans-serif;
}

.container-wrapper {
	width: 100%;
	padding: 20px;
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
	justify-content: center;
}

.search-result {
	width: 100%;
	padding: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.book-card {
	width: 250px;
	height: 350px;
	border: 1px solid gray;
	border-radius: 15px;
	overflow: hidden;
	padding: 5px;
	cursor: pointer;
	position: relative;
}

.book-card .badge {
	position: absolute;
	top: 1%;
	right: 2%;
	background: white;
	font-weight: 400 !important;
	color: green;
	border: 1px solid green !important;
}

.book-card:hover {
	box-shadow: 2px 3px 6px gray, -2px -3px 6px gray !important;
}

.book-card .not-available {
	color: red !important;
	border: 1px solid red !important;
}

.book-card img {
	width: 100%;
	height: 230px;
	border-radius: 15px;
	border-top-right-radius: 15px;
}

.book-card h1 {
	font-size: 1.3rem;
	color: orange;
}

.author-title {
	font-size: 0.8rem !important;
	font-weight: 500 !important;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.categories {
	font-size: 0.8rem !important;
	font-weight: 500 !important;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.pagination {
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 9px;
}

.pagination .pagination-btn {
	text-decoration: none;
	padding: 1px;
	width: 30px;
	font-size: 0.8rem;
	height: 30px;
	display: flex;
	justify-content: center;
	align-items: center;
	border-radius: 3px;
	background: white !important;
}

.pagination .active {
	color: white !important;
	background: var(--color-blue) !important;
}

.search-result input {
	outline: none;
	padding: 3px 6px;
	border-radius: 6px;
	border: 1px solid black;
	border-radius: 6px;
}

.search-result select {
	padding: 3px 7px;
	border-radius: 6px;
}

.search-result select option {
	font-size: 0.85rem;
}

.search-result select option:checked {
	background: var(--color-blue) !important;
	color: white !important;
}

.search-result form button {
	font-size: 0.9rem;
	border-radius: 10px;
	background: var(--color-blue);
	color: white;
	padding: 2px 10px;
	outline: none;
	border: none;
}

.not-found {
	display: flex;
	justify-content: center;
	align-items: center;
}

.custom-btn-add{
 font-size: 0.9rem;
 text-decoration:none;
	border-radius: 10px;
	background: var(--color-blue);
	color: white;
	padding: 2px 10px;
	outline: none;
	border: none;
}

.custom-btn-add:hover {
	color: white;
}

</style>

<body>
	<%

	int totalData = (int) request.getAttribute("totalBooks");
	int currentPage = (int) request.getAttribute("page");
	int limit = (int) request.getAttribute("limit");
	int noOfPages = (int) Math.ceil((double) totalData / limit);
	String searchText = (String) request.getAttribute("searchText");
	%>
	<%@include file="/admin/NavBar.jsp"%>
	<%
	String selectedCategory = request.getAttribute("selectedCategory") != null
			? (String) request.getAttribute("selectedCategory")
			: null;
	%>
	<section class="search-result">
		<div>
			<h1>Books</h1>
			<h6>
				Search Results:
				<%=request.getAttribute("totalBooks")%>
			</h6>
		</div>
		<div class="d-flex justify-content-center align-items-center" style="gap:5px;">
		    
		   <% 
		     if(user.getRole().equalsIgnoreCase("admin"))
		     {
		    	 %>
		    	  <a class="me-2 custom-btn-add" href="<%=request.getContextPath()+"/book/AddBook.jsp" %>">Add Book</a>
		    	 <% 
		     }
		   %>
		
			<form action="<%=request.getContextPath() + "/books/getAllBooks"%>"
				method="get" onsubmit="return removeEmptyFields()">
				<input type="hidden" name="page" value="1"> <input
					type="hidden" name="limit" value="<%=limit%>"> <input
					type="text" placeholder="Search Book by title" name="q" value="<%= searchText!=null ?searchText :""%>"
					id="searchQuery"> <select name="category"
					id="searchCategory">
					<option value="all"
						<%=selectedCategory != null && selectedCategory.equalsIgnoreCase("all") ? "selected" : ""%>>
						All Categories</option>

					<%
					List<Category> categories = (List) request.getAttribute("categories");
					for (Category c : categories) {
						boolean isSelected = c.getName().equals(selectedCategory);
					%>
					<option value="<%=c.getName()%>" <%=isSelected ? "selected" : ""%>><%=c.getName()%></option>
					<%
					}
					%>
				</select>

				<button type="submit">Search</button>
			</form>
		</div>
	</section>
	<%
	List<Book> books = (List) request.getAttribute("books");

	if (books.size() > 0)

	{
	%>

	<section class="mb-3">
		<div class="container-wrapper">
			<%
			for (Book b : books) {
			%>
			<div class="book-card" onclick="handleBookClick('<%=b.getBookId()%>')">
				<span
					class="badge <%=b.getAvailable() == 0 ? "not-available" : ""%>"><%=b.getAvailable() == 0 ? "Not Available" : "Available"%></span>
				<img alt="" src="<%=b.getImg()%>" loading="lazy">
				<div class="mt-2">
					<h1 class="text-center"><%=b.getTitle()%></h1>
					<div class="book-details">
						<h6 class="text-sm author-title">
							Author :
							<%=b.getAllAuthorsName()%></h6>
						<h6 class="text-sm mt-2 categories">
							Categories :
							<%=b.getAllCategoriesName()%></h6>
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>
		<div class="pagination">
			<%
			int startPage = Math.max(1, currentPage - 2);
			int endPage = Math.min(noOfPages, currentPage + 2);

			//startPage: Sets the starting page number to show in the pagination. It takes the maximum of either 1 (the first page) or two pages before the currentPage (i.e., currentPage - 2). This prevents showing a negative or zero page number.
			//endPage: Sets the ending page number to show in the pagination. It takes the minimum of either noOfPages (the total number of pages) or two pages after the currentPage (i.e., currentPage + 2). This ensures you don’t display page numbers beyond the total available pages

			// Display the "Previous" button
			if (currentPage > 1) {
			%>
			<a
				href="?page=<%=currentPage - 1%>&limit=<%=limit%><%=selectedCategory!=null ? "&category="+selectedCategory:"" %><%= searchText!=null ?"&q="+searchText :""%>"
				class="pagination-btn">Prev</a>
			<%
			}

			for (int i = startPage; i <= endPage; i++) { 
			if (i == currentPage) {
			%>
			<span class="pagination-btn active"><%=i%></span>
			<%
			} else {
			%>
			<a
				href="?page=<%=i%>&limit=<%=limit%><%=selectedCategory!=null ? "&category="+selectedCategory:"" %><%= searchText!=null ?"&q="+searchText :""%>"
				class="pagination-btn"><%=i%></a>
			<%
			}
			}

			if (currentPage < noOfPages) {
			%>
			<a
				href="?page=<%=currentPage + 1%>&limit=<%=limit%><%=selectedCategory!=null ? "&category="+selectedCategory:"" %><%= searchText!=null ?"&q="+searchText :""%>"
				class="pagination-btn">Next</a>
			<%
			}
			%>
		</div>

	</section>
	<%
	} else {
	%>
	<section class="not-found">No Books Found</section>
	<%
	}
	%>


	<script>
		function removeEmptyFields() {
			const searchQuery = document.getElementById("searchQuery");
			const searchCategory = document.getElementById("searchCategory");
			// Remove the 'q' input if it is empty
			if (!searchQuery.value.trim()) {
				searchQuery.name = "";
			}

			if (searchCategory.value.trim() === "all") {
				searchCategory.name = "";
			}

			return true; // Allow form submission to proceed
		}
		
		function handleBookClick(id)
		{
			window.location.href = `<%=request.getContextPath()+"/books/getBook?id="%>${id}`
		}
	</script>
</body>
</html>