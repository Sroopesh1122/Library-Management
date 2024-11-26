<%@page import="com.library.dao.CategoryDaoImpl"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.library.dto.Category"%>
<%@page import="com.library.dto.Book"%>
<%@page import="com.library.dto.Author"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Book</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
</head>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>
<%
List<Category> categories = new CategoryDaoImpl().getAllCategories(null, 0, 0);
%>
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
	gap: 4px;
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

#bookForm {
	width: 500px !important;
}

#selectedOptions {
	display: flex;
	flex-wrap: wrap;
	gap: 5px;
}

.details-wrapper-user div {
	margin: 15px 0px;
}

.details-wrapper-user div span:first-child {
	font-size: 1.1rem;
	color: var(--color-blue);
}

.details-wrapper-user div .not-available {
	border: 1px solid red;
	border-radius: 10px;
	color: red;
	font-size: 0.9rem;
	text-align: center;
}

.details-wrapper-user div .available {
	border: 1px solid green;
	border-radius: 10px;
	color: green;
	font-size: 0.9rem;
	text-align: center;
}

.details-wrapper-user div button {
	width: 100%;
	text-align: center;
	border-radius: 10px;
	margin-top: 5px;
	padding: 5px;
	border: none;
	background: var(--color-blue);
	color: white;
}
#img-file{
 display: none;
}
#upload-btn{
 padding: 4px;
 border-radius: 10px;
 background: var(--color-blue);
 border: none;
 color: white;
}
.custom-btn
{
 padding: 6px;
 border-radius: 10px;
 background: orange;
 border: none;
 color: white;
 font-size: 0.9rem;
}

</style>


<body>
	<%@include file="/admin/NavBar.jsp"%>
	<a href="<%=request.getContextPath() + "/books/getAllBooks"%>">Back</a>
	<h3 class="my-2 text-center">Book Details</h3>

	<section class="main-wrapper">

		<div class="img-wrapper">
			<img alt="" src="" width="400px" height="600px" id="img-preview">
		</div>
		<div class="details-wrapper">


			<form class="needs-validation" action="<%=request.getContextPath()+"/books/register" %>" novalidate  enctype="multipart/form-data"  method="post">
			
			
			    <input type="file" onchange="handleFileChange(this)" accept="image/*" id="img-file" name="img">
			
				<div class="mb-3">
					<label for="title" class="form-label">Title</label> <input
						type="text" class="form-control" name="title" id="title" required>
					<div class="invalid-feedback error">Please enter a Title.</div>
				</div>
				<div class="mb-3">
					<label for="publisher" class="form-label">Publisher</label> <input
						type="text" class="form-control" name="publisher" id="publisher" required>
					<div class="invalid-feedback error">Please enter publisher name</div>
				</div>
				<div class="mb-3">
					<label for="year" class="form-label">Published Year</label> <input
						type="text" class="form-control" name="year" id="year" required>
					<div class="invalid-feedback error">Please provide published year</div>
				</div>
				
				<div class="mb-3">
					<label for="author" class="form-label">Authors</label> <input
						type="text" class="form-control" name="authors" id="author" required>
					<div class="invalid-feedback error">Please enter authors</div>
				</div>
				
				<input type="hidden" name="categories" id="categories" >
				<div class="mb-3 ">
				   <label for="category" class="form-label">Categories</label>
				   <div id="selectedOptions"></div>
					<select onchange="handleCategoryChange(this)" name="category"
						id="category" class="form-control">
						<option value="">Select Options</option>
						<%
						for (Category c : categories) {
						%>
						<option value="<%=c.getName()%>"><%=c.getName()%></option>
						<%
						}
						%>
					</select>
					<div class="error" id="category-error">Choose atleast one category</div>
				</div>
				
				<div class="mb-2">
				   
				   <button onclick="handleUploadClick()" type="button" id="upload-btn" >Upload Img</button><span class="ms-1" id="upload-file-name">Select Image</span>
				    <div class="error mt-1" id="upload-error"></div>
				</div>
				
				<div class="d-flex justify-content-center align-items-center mt-2">
				   <button type="submit" class="custom-btn">Submit</button>
				</div>
			</form>





		</div>

	</section>

	<script type="text/javascript"
		src="<%=request.getContextPath() + "/book/BookJspJs.js"%>"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script>
	// Load book details initially
	
	    let selectedList = [];
		const selectedOptions = $("#selectedOptions");
		const message = "<%=request.getAttribute("message")%>"
		if(message !== "null")
			{
			  const errorMsg = "<%=request.getAttribute("error")%>"
			  if(errorMsg === "null")
			   {
				  Swal.fire({
			            title: 'Success',
			            text: message,
					    icon : 'success',
				      });
				}
			  if(errorMsg === "true"){
					Swal.fire({
			            title: 'Success',
			            text: message,
					    icon : 'error',
				      });
				}
			  
			   <%request.removeAttribute("message");%>
			}

		const options=[];
		
         //Fill options
		<%for (Category c : categories) {%>
			   options.push({id:<%=c.getCategoryId()%> ,name:'<%=c.getName()%>'})
			  <%}%>
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
		
		function showUploadError()
		{
			$("#upload-error").show();
			$("#upload-error").text("Upload Book img")
		}
		function hideUploadError()
		{
			$("#upload-error").hide();
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
		$("#category-error").hide();
	
		(function () {
		      'use strict';

		      // Fetch all the forms we want to apply custom validation styles to
		      var forms = document.querySelectorAll('.needs-validation');

		      // Loop over them and prevent submission
		      Array.prototype.slice.call(forms).forEach(function (form) {
		        form.addEventListener('submit', function (event) {
		          if (!form.checkValidity()) {
		            event.preventDefault();
		            event.stopPropagation();
		          }
		          if(selectedList.length === 0)
		          {
		        	  event.preventDefault();
			          event.stopPropagation();
		        	  showCategoryError()
		          }
		          
		          const categoryData = options.filter((d)=>(selectedList.find((s)=>s === d.name)))
		          
		          const modifyKeys = categoryData.map((d)=>{return {categoryId:d.id , name:d.name}})
		          
		          $("#categories").val(JSON.stringify(modifyKeys))
		          
		          
		          const fileInput = $("#img-file")[0];
				  const files = fileInput.files;
				  if(files.length === 0)
				    {
					  event.preventDefault();
			            event.stopPropagation();
					  showUploadError()
					}else{
						hideUploadError()
					}
				  
		          form.classList.add('was-validated');
		        }, false);
		      });
		    })();

	  
		function handleFileChange()
		{
			const fileInput = $("#img-file")[0];
			const files = fileInput.files;
			
			
			if (files.length > 0) {
                const file = files[0];
                $("#upload-file-name").text(file.name)
                const preview = document.getElementById("img-preview");
                const fileUrl = URL.createObjectURL(file);
                preview.src = fileUrl;
                preview.style.display = "block";
                hideUploadError()
            } else {
                showUploadError()
                $("#upload-file-name").text("Select Image");
                const preview = document.getElementById("img-preview");
                preview.src = "";
            }
			
		}
		
		
		
		function handleUploadClick()
		{
			 $("#img-file").click();
			
		}
		
		
		

	</script>
</body>
</html>

