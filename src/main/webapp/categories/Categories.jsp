<%@page import="com.library.dao.CategoryDao"%>
<%@page import="com.library.dao.CategoryDaoImpl"%>
<%@page import="com.library.dto.Category"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Categories</title>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>
<%request.setAttribute("menu", "Categories"); %>
<%
 CategoryDao categoryDao= new CategoryDaoImpl();
 String searchText = (request.getParameter("q")!=null && !request.getParameter("q").toString().equalsIgnoreCase("")) ? request.getParameter("q") : null;

 int totalData = categoryDao.getTotalCategories(searchText);
 int currentPage =  request.getParameter("page")!=null ? Integer.parseInt((String)request.getParameter("page")):1;
 int limit =  request.getParameter("limit")!=null ? Integer.parseInt((String)request.getParameter("limit")):10;
 List<Category> categories = categoryDao.getAllCategories(searchText,currentPage,limit);
%>
</head>
<style>
body {
	min-height: 100vh;
	width: 100%;
	font-family: "Outfit", sans-serif;
}
.wrapper{
  width: 70%;
  margin: 0 auto;
  
}
.wrapper h1{
 font-size: 1.2rem;
 color: var(--color-blue);
}
.wrapper form{
 display: flex;
 justify-content: flex-start;
 align-items: center;
 gap:10px;
}
.wrapper form input{
 border: 1px solid black; 
 border-radius: 10px;
 font-size: 0.97rem;
}
.wrapper form button {
	border: 1px solid var(--color-blue);
	background: var(--color-blue);
	color: white;
	padding: 2px;
	font-size: 0.8rem;
	border-radius: 7px;
}
.wrapper h3{
 font-size: 0.9rem;
 color: var(--color-blue)
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

.category-wrapper{
 width: 100%;
 padding: 2px;
}
.category-wrapper div{
 padding:5px;
 margin: 10px auto;
}
.category-wrapper .header{
   background: #ededed;
   width: 100%;
   display: flex;
}
.custom-table{
 
 border: 1px solid #efefef;
 border-radius: 10px;
}

.category-wrapper .data-row
{ 
   width:100% !important;
  transition:all 0.5s;
  display: flex;
}

.category-wrapper .data-row:hover{
 background: #ededed;
}
.category-wrapper .header span:nth-child(1),.category-wrapper .data-row span:nth-child(1) {
    overflow:hidden;
	width: 10% !important;
}
.category-wrapper .header span:nth-child(2) ,.category-wrapper .data-row span:nth-child(2)  {
padding-left:5px;
	width: 70% !important;
}
.category-wrapper .header span:nth-child(3) ,.category-wrapper .data-row span:nth-child(3)  {
	width: 20% !important;
}

.data-row .fa-trash-can{
 cursor: pointer;
}
.info-div{
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.custom-btn{
 background: var(--color-blue) !important;
 color: white;
 font-size: 0.8rem;
 border-radius: 10px;
 border: none;
 padding: 2px;
 text-decoration: none;
 cursor: pointer;
}
.custom-btn:hover {
    background:var(--color-blue);
	color: white;
}
.input-error-border{
 border: 1px solid red;
}

.custom-btn-lg{
 background: var(--color-blue) !important;
 color: white;
 border-radius: 10px;
 border: none;
 padding: 2px;
 text-decoration: none;
 cursor: pointer;
}
.custom-btn-lg:hover{
   color: white;
}

.was-validated .form-control:valid {
	background-image: none;
}

.was-validated .form-control:invalid {
	background-image: none;
}
.view-book{
 font-size: 0.8rem;
 color: var(--color-blue);
 cursor: pointer;
}
.view-book:hover {
	text-decoration: underline;
}
</style>
<body>
   <%@include file="/admin/NavBar.jsp"%>
    
    <div class="container wrapper">
     <h1 class="text-center">All Categories</h1>
     <form action="<%=request.getContextPath()+"/categories/Categories.jsp" %>">
        <input placeholder="Seach for category" type="text" name="q">
        <input type="hidden" name="page" value="<%=currentPage %>">
         <input type="hidden" name="limit" value="<%=limit %>">
        <button>Search</button>
     </form>
     <div class="info-div">
          <h3 class="mt-4">Total Search <%=totalData %></h3>
          <button type="button" class="btn btn-primary custom-btn" data-bs-toggle="modal" data-bs-target="#exampleModal">
            Add Category
           </button>
     </div>
       
       <div class="custom-table">
           <div class="category-wrapper">
         <div class="header">
           <span>CategoryID</span>
           <span>Name</span>
           <span>Action</span>
         </div>
         <div class=table-data>
         </div>
       </div>
       </div>
       
    </div>
    <div class="pagination mt-2">
			<%
			int startPage = Math.max(1, currentPage - 2);
			int noOfPages = (int) Math.ceil((double) totalData / limit);
			int endPage = Math.min(noOfPages, currentPage + 2);
			String queryText = searchText!=null ? "&q="+searchText:"";
			

			if (currentPage > 1) {
			%>
			<a
				href="<%=request.getContextPath()+"/categories/Categories.jsp"%>?page=<%=currentPage - 1%>&limit=<%=limit+queryText%>"
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
				href="<%=request.getContextPath()+"/categories/Categories.jsp"%>?page=<%=i%>&limit=<%=limit+queryText%>"
				class="pagination-btn"><%=i%></a>
			<%
			}
			}

			if (currentPage < noOfPages) {
			%>
			<a
				href="<%=request.getContextPath()+"/categories/Categories.jsp"%>?page=<%=currentPage + 1%>&limit=<%=limit+queryText%>"
				class="pagination-btn">Next</a>
			<%
			}
			%>
		</div>  
		
		<!-- Button trigger modal -->


<!-- Modal -->

<div class="modal fade " id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Add Category</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="<%=request.getContextPath() + "/category"%>" method="post" class="needs-validation" novalidate>
           <div class="form-group">
              <label for="cat-name">Category Name</label>
               <input type="text" class="form-control" id="cat-name" name="categoryName" required>
                <div class="invalid-feedback error">Please enter a category name.</div>
           </div>

    <div class="d-flex justify-content-center align-items-center">
        <button class="btn mt-2 custom-btn-lg px-3" type="submit">Add</button>
    </div>
</form>

      </div>
    </div>
  </div>
</div>

		
		
		  
</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
 <script type="text/javascript">
 
 
 <%
   if(request.getAttribute("error")!=null)
   {
	   %>
	   Swal.fire({
           title: 'Oops!',
           text: "<%=request.getAttribute("error").toString()%>",
		icon : 'error',
	});
	   <%
   }
 
 if(request.getAttribute("success")!=null)
 {
	   %>
	   Swal.fire({
         title: 'Success',
         text: "<%=request.getAttribute("success").toString()%>",
		icon : 'success',
	});
	   <%
 }
 %>



 
  const data=[]
  <%
     for(Category c:categories)
     {
	   %>
	     data.push({"categoryId":<%=c.getCategoryId()%>,"name":'<%=c.getName()%>'})
	   <% 
     }
%>


function handleCategoryDelete(val)
{
 window.location.href=`<%=request.getContextPath()+"/category?id="%>${val}`
}

function handleBookClick(val)
{
 window.location.href=`<%=request.getContextPath()+"/books/getAllBooks?category="%>${val}`
}


$(document).ready(function(){
	(function () {
		  'use strict'

		  // Fetch all the forms we want to apply custom Bootstrap validation styles to
		  var forms = document.querySelectorAll('.needs-validation')

		  // Loop over them and prevent submission
		  Array.prototype.slice.call(forms)
		    .forEach(function (form) {
		      form.addEventListener('submit', function (event) {
		        if (!form.checkValidity()) {
		          event.preventDefault()
		          event.stopPropagation()
		        }

		        form.classList.add('was-validated')
		      }, false)
		    })
		})()
	
	
	
	<%
	 if(user.getRole().equalsIgnoreCase("admin"))
	 {
		 %>
		 $(".table-data").empty();
		 data.forEach((ele)=>(
				  $(".table-data").append(`<div class="data-row">
		       	       <span style="width: 10%">${ele.categoryId}</span>
		      	        <span>${ele.name}</span>
		      	        <span><i class="fa-solid fa-trash-can" onclick="handleCategoryDelete('${ele.categoryId}')"></i></span>
		      	     </div>`)))		 
		 <%
	 }
	 else{
		 
		 %>
		 $(".table-data").empty();
		 data.forEach((ele)=>(
				  $(".table-data").append(`<div class="data-row">
		       	       <span style="width: 10%">${ele.categoryId}</span>
		      	        <span>${ele.name}</span>
		      	        <span class="view-book" onclick="handleBookClick('${ele.name}')">See All books</span>
		      	     </div>`)))
		 
		 <%
	 }
	%>
	
}())


 </script>

</html>