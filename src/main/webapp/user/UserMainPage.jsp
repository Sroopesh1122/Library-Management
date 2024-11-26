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
<title>E-Library</title>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>
<%request.setAttribute("menu", "Home"); %>
</head>
<style>
body {
	min-height: 100vh;
	width: 100%;
	font-family: "Outfit", sans-serif;
}

.user-main{
 width: 100%;
 height: 88vh;
 overflow: hidden;
 position: relative;
 display: flex;
 justify-content: center;
 align-items: center;
}

.user-main .book-1
{
  position: absolute;
  top: 10%;
  right: 10%;
  width: 300px;
  height: 300px;
}
.bubble-1{
  border-radius:50%;
  box-shadow:3px 5px 8px black !important;
  position: absolute;
  top: 10%;
  left: 10%;
  width: 100px;
  height: 100px;
  background-image: linear-gradient(
    45deg,
    hsl(0deg 0% 100%) 0%,
    hsl(40deg 100% 94%) 15%,
    hsl(40deg 100% 88%) 30%,
    hsl(40deg 100% 83%) 44%,
    hsl(40deg 100% 77%) 59%,
    hsl(40deg 100% 71%) 74%,
    hsl(41deg 100% 64%) 88%,
    hsl(42deg 100% 55%) 100%
  );

}
.search-box h1{
 text-shadow: 1px 3px 2px var(--color-blue);
 color: var(--color-blue);
}
form input{
 border-radius: 10px;
 border: 1px solid black;
 padding: 4px 0px;
 width: 500px;
}
form button{
 padding: 4px;
 border-radius: 8px;
 background: var(--color-blue);
 color: white;
 border: none;
 font-size: 0.95rem;
}


</style>

<body>

	<%@include file="/admin/NavBar.jsp"%>
	
	
	<section class="user-main">
	  <div class="bubble-1"></div>
	  <img alt="" class="book-1" src="https://i.pinimg.com/originals/5b/f0/a3/5bf0a3e0601d35349c5451fa52138ea6.gif">
	   
	   	  
	  <div class="search-box">
	        <h1>Search Books</h1>
			<form action="<%=request.getContextPath() + "/books/getAllBooks"%>"
				method="get" onsubmit="return handleFormSubmit()">
				<input type="text" placeholder="Search Book by title" name="q" id="searchQuery"> <button type="submit">Search</button>
			</form>
		</div>
	  
	  
	</section>
	
	<script type="text/javascript">
	 
	  function handleFormSubmit() {
		  
		  if($("#searchQuery").val() === "")
		  {
			 return false;  
		  }
		  return true;
	}
	
	</script>
	
</body>
</html>