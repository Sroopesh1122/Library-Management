<%@page import="java.util.Iterator"%>
<%@page import="com.library.dto.Category"%>
<%@page import="com.library.dto.Book"%>
<%@page import="com.library.dto.Author"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<Author> authors = (List) request.getAttribute("authors");
Book book = (Book) request.getAttribute("book");
List<Category> categories = (List) request.getAttribute("categories");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=book.getTitle()%></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
</head>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>
<%
request.setAttribute("menu", "Books");
%>
<style>
body {
	min-height: 100vh;
	width: 100%;
	font-family: "Outfit", sans-serif;
}

.main-wrapper {
	width: 100%;
	height: 100vh;
	padding: 20px;
	display: flex;
	justify-content: center;
	align-items: flex-start;
	gap: 40px;
}

.was-validated .form-control:valid {
	background-image: none;
}

.was-validated .form-control:invalid {
	background-image: none;
}

.img-wrapper {
	border-radius: 10px;
	overflow: hidden;
}

.details-wrapper {
	display: flex;
	justify-content: center;
	align-items: flex-start;
	flex-direction: column;
	padding: 20px;
}

.tag {
	font-size: 0.8rem !important;
	background: #f1f1f1;
	padding: 4px;
	border-radius: 10px;
	display: flex;
	justify-content: center;
	align-items: center;
	gap:4px;
	width: fit-content;
	cursor: pointer;
}

.update-btn {
	width: fit-content;
	margin: 0 auto;
	background: #363CFF;
	color: white;
	border-radius: 20px;
}

.error-inputBox {
	border-color: red !important;
}
#bookForm{
 width: 500px !important;
}
#selectedOptions
{
 display: flex;
 flex-wrap: wrap;
 gap:5px;
}

.details-wrapper-user div
{
  margin: 15px 0px;
}
.details-wrapper-user div span:first-child {
	font-size: 1.1rem;
	color: var(--color-blue);
}
.details-wrapper-user div .not-available
{
  border: 1px solid red;
  border-radius: 10px;
  color: red;
  font-size:0.9rem;
  text-align: center;
}

.details-wrapper-user div .available
{
  border: 1px solid green;
  border-radius: 10px;
  color: green;
  font-size:0.9rem;
  text-align: center;
}
.details-wrapper-user div button{
 width: 100%;
 text-align: center;
 border-radius: 10px;
 margin-top: 5px;
 padding: 5px;
 border:none;
 background: var(--color-blue);
 color: white;
}
.no-decoration{
 text-decoration: none !important;
}


</style>
<body>
	<%@include file="/admin/NavBar.jsp"%>
    <div class="mt-1 px-5">
       <a class="no-decoration" href="<%=request.getContextPath()+"/books/getAllBooks" %>"><i class="fa-solid fa-circle-chevron-left me-1"></i> Back</a>
    </div>
	<h3 class="my-2 text-center">Book Details</h3>
	<section class="main-wrapper">

		<div class="img-wrapper">
			<img alt="<%=book.getTitle()%>" src="<%=book.getImg()%>" width="400px" height="600px">
		</div>
		<%
		 if(user.getRole().equalsIgnoreCase("admin"))
		 {
			 %>
			  <div class="details-wrapper">

			<form action="<%=request.getContextPath()+"/books/update" %>" method="post" id="bookForm" class="needs-validation" novalidate>
                      
                      <input type="hidden" name="formData" id="formDataInput">

					<div class="mb-3">
						<label for="book_id" class="form-label">Book Id</label> <input
							type="text" class="form-control" id="book_id" name="bookId"
							disabled>
					</div>

					<div class="mb-3">
						<label for="title" class="form-label">Title</label> <input
							type="text" class="form-control" id="title" name="title" required>
						<div class="invalid-feedback error">Title is required</div>
					</div>

				<div class="mb-3">
					<label for="authors" class="form-label">Authors</label> <input
						type="text" class="form-control" id="authors" name="authors"
						required>
					<div class="invalid-feedback error">Authors are required</div>
				</div>

				<div class="mb-3">
					<label for="publisher" class="form-label">Published By</label> <input
						type="text" class="form-control" id="publisher" name="publisher"
						required>
					<div class="invalid-feedback error">Publisher is required</div>
				</div>

				<div class="mb-3">
					<label for="year" class="form-label">Published Year</label> <input
						type="text" class="form-control" id="year" name="year" required>
					<div class="invalid-feedback error">Published year is
						required</div>
				</div>

				<div id="selectedOptions"></div>

				<div class="mb-3">
					<label for="year" class="form-label">Category</label>
					 <select onchange="handleCategoryChange(this)" name="category" id="category" class="form-control">
						<option value="">Select Options</option>
						<%
						for (Category c : categories) {
						%>
						<option value="<%=c.getName()%>"><%=c.getName()%></option>
						<%
						}
						%>
					</select>
					<div class="invalid-feedback error" id="category-error">Category required</div>
				</div>

				<button type="submit" class="btn btn-primary mt-3">Update</button>
			</form>

		</div>
			 <% 
		 }
		 else{
			 %>
			 
		<div class="details-wrapper-user">

            
            <div>
             <span>Book ID : </span><span><%=book.getBookId() %></span>
            </div>
		     
		    <div>
             <span>Title : </span><span><%=book.getTitle() %></span>
            </div>
            
            <div>
             <span>Publisher : </span><span><%=book.getPublisher()%></span>
            </div>
            <div>
             <span>Published Year : </span><span><%=book.getPublishedYear() %></span>
            </div>
            
            <div>
             <span>Author : </span><span><%=book.getAllAuthorsName() %></span>
            </div>
            
            <div>
             <span>Categories : </span><span><%=book.getAllCategoriesName() %></span>
            </div>
            
            <div>
             
            
            	  <div class="<%=book.getAvailable() == 0 ? "not-available":"available" %>"><%=book.getAvailable() == 0 ? "Not available":"Available" %></div>
            
                 <button type="button" onclick="handleBuyClick()"  <%=book.getAvailable() == 0 ? "disabled":"" %> >Buy</button>
            </div> 
             
             

		</div>
			 
			 
			 
			 <% 
		 }
		%>
	</section>

	<script type="text/javascript" src="<%=request.getContextPath() + "/book/BookJspJs.js"%>"></script>
 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script>
	// Load book details initially
	    <%
	      if(session.getAttribute("borrowed_success")!=null)
	      {
	    	  %>
	    	  Swal.fire({
		            title: 'Success',
		            text: "Book Borrowred Successfully!",
				    icon : 'success',
			      });
	    	  <%
	    	  session.removeAttribute("borrowed_success");
	      }
	    
	    %>
	
	
	    let selectedList = [];
		const selectedOptions = $("#selectedOptions");
		const message = "<%=request.getAttribute("message")%>"
		if(message !== "null")
			{
			  Swal.fire({
	            title: 'Success',
	            text: message,
			    icon : 'success',
		      });
			   <%request.removeAttribute("message");%>
			}

		const options=[];
		
         //Fill options
		<%
		  for(Category c : categories)
		  {
			  %>
			   options.push({id:<%=c.getCategoryId()%> ,name:'<%= c.getName()%>'})
			  <%
		  }
		%>
		 //Fill bookData
		 <%
		  for(Category c : book.getCategories())
		  {
			  %>
			  selectedList.push('<%= c.getName()%>')
			  <%
		  }
		%>
		 showOptions()
		 
		function chekoutSelectedList(val)
		{
			return selectedList.find((d)=>d === val);
		}
		 

			function showCategoryError()
			{
				$("#category-error").show()
				 $("#category").addClass("error-inputBox")
				$("#category-error").text("Choose atleast one category!")
			}
			function hideCategoryError()
			{
				$("#category-error").hide()
				 $("#category").removeClass("error-inputBox")
			}
		
		function removeOption(val)
		{
		   selectedList = selectedList.filter((d)=>d!==val)
		    if(selectedList.length > 0)
		  {
			  hideCategoryError()
		  }else{
			  showCategoryError()
		  }
			showOptions()
		}
		
		function showOptions()
		{
			selectedOptions.empty()
			selectedList.map((d) => selectedOptions.append(`<span class='tag' onclick='removeOption("${d}")'>${d} <i class="fa-solid fa-xmark"></i></span>`)); 
		}
		
		function handleCategoryChange(e)
		{
			
		  if(chekoutSelectedList(e.value))
		   {
			  selectedList = selectedList.filter((d)=>d !== e.value)
		   }else{
			 selectedList.push(e.value)
		   }
		  $("#category").val("")
		  if(selectedList.length > 0)
		  {
			  hideCategoryError()
		  }else{
			  showCategoryError()
		  }
		  showOptions()
		}
	
		
		
	
	
	$(document).ready(function () {
		$("#title").val("<%=book.getTitle()%>");
		$("#authors").val("<%=book.getAllAuthorsName()%>");
		$("#book_id").val("<%=book.getBookId()%>");
		$("#publisher").val("<%=book.getPublisher()%>");
		$("#year").val("<%=book.getPublishedYear()%>");
		// Bootstrap validation on form submission
		(function() {
			'use strict'
			const form = document.querySelector('.needs-validation');
			form.addEventListener('submit', function(event) {
				if (!form.checkValidity()) {
					event.preventDefault();
					event.stopPropagation();
					return
				}
				if(selectedList.length == 0)
			     {
					event.preventDefault();
					event.stopPropagation();
					event.preventDefault();
					event.stopPropagation();
					showCategoryError()
					return 
			     }
				let updateData=<%=book.toJson()%>;
				updateData.title = $("#title").val();
				updateData.authors =[{authorId : <%=book.getAuthors().get(0).getAuthorId()%>,name: $("#authors").val()}];
				updateData.publisher = $("#publisher").val();
				updateData.publishedYear = $("#year").val();
				const categoryIds=[]
				options.map((d)=>{
					if(selectedList.find((data)=>data === d.name))
					 {
					   categoryIds.push({categoryId:d.id,name:d.name})	
				     }
				})
				updateData.categories = categoryIds;
				const {allAuthorsName,allCategoriesName,...rest} = updateData
				$("#formDataInput").val(JSON.stringify(rest)) 
				form.classList.add('was-validated');
			}, false);
		})();
	});
	
	
	//fucntion to borrowBook
	
	function handleBuyClick()
	{
		window.location.href = '<%=request.getContextPath()+"/book/BorrowBook.jsp?bookId="+book.getBookId()%>'
		
	}
	

	</script>
</body>
</html>

