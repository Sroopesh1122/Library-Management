<%@page import="com.library.dto.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign In</title>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>
<style type="text/css">
body {
	min-height: 100vh;
	width: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	font-family: "Outfit", sans-serif;
}

.signin-container {
	width: 400px;
	padding: 20px;
	border: 1px solid gray;
	box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3) !important;
	border-radius: 20px;
}

.signin-container h2 {
	color: #1921FF;
}

.signin-container form {
	display: flex;
	justify-content: center;
	flex-direction: column;
}

.signin-btn {
	width: fit-content;
	margin: 0 auto;
	background: #363CFF;
	color: white;
	border-radius: 20px;
}

.signin-btn:hover {
	color: white;
	background: #1921FF;
}

.form-group label {
	font-size: 0.9rem;
}

.signin-footer {
	font-size: 0.85rem;
}

.signin-footer a {
	text-decoration: none;
}

.signin-footer a:hover {
	text-decoration: underline;
}

.divider {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.divider div {
	display: flex;
	flex: 1;
	background: gray;
	height: 1px
}

.divider span {
	font-size: 0.8rem;
	margin: 0 5px;
}
</style>
</head>
<body>
	<div class="container mt-5 signin-container">
		<h2 class="mb-4 text-center signup-header">SignIn</h2>
		<form action="<%=request.getContextPath() + "/usercontroller"%>"
			method="post" id="signinForm" novalidate>
			<input type="hidden" name="action" value="login">

			<div class="form-group">
				<label for="email">Email</label> <input type="email"
					class="form-control" id="email" name="email" required>
				<div class="invalid-feedback error">Please enter a valid email
					address.</div>
			</div>


			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					class="form-control" id="password" name="password" required>
				<div class="invalid-feedback error">Please enter password</div>
			</div>


			<button type="submit" class="btn mt-3 signin-btn">SignIn</button>

			<div class="divider mt-2">
				<div></div>
				<span>OR</span>
				<div></div>
			</div>
			<p class="text-center signin-footer">
				Don't have an account ? <a href="<%=request.getContextPath()+"/user/SignUp.jsp" %>">Register</a>
			</p>

		</form>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script>
		$(document).ready(function() {
			$('#signinForm').on('submit', function(event) {
				event.preventDefault();
				const password = $('#password').val();
				const email =  $("#email").val();
				
				if (this.checkValidity() === false) {
					$(this).addClass('was-validated');
					return false;
				}else{
					if(!email || !password)
					{
						return false;
					}
				}
				this.submit();
			});
		});
	</script>


</body>
<%@include file="../Alert.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script type="text/javascript">
   <%if (request.getAttribute("errorMsg") != null) {
	String errorMsg = (String) request.getAttribute("errorMsg");
	request.removeAttribute("errorMsg");%>
        Swal.fire({
            title: 'Oops!',
            text: "<%=errorMsg%>",
		icon : 'error',
	});
<%}%>


   <%--
     $(document).ready(function(){
	
	   fetch("<%=request.getContextPath()+"/books/"%>", {
		   method: 'GET',
		 })
		   .then((response)=>response.json())
		   .then(data => console.log(data))
		   .catch(error => console.error('Error:', error));
	   
	   
	   fetch("<%=request.getContextPath()+"/books/register"%>", {method: 'POST'})
 	   .then((response)=>response.text())
 	   .then(data => console.log(data))
 	   .catch(error => console.error('Error:', error))
   })
   
   --%>
   
   
		   
		   
		  
  
		   
   
   

	
</script>



</html>