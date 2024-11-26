<%@page import="com.library.dto.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
<%@include file="/commonUtils/FontBootstrapCSS.jsp" %>
<style type="text/css">
body {
	min-height: 100vh;
	width: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	font-family: "Outfit", sans-serif;
}

.signup-container {
	width: 400px;
	padding: 20px;
	border: 1px solid gray;
	box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3) !important;
	border-radius: 20px;
}

.signup-container h2 {
	color: #1921FF;
}

.signup-container form {
	display: flex;
	justify-content: center;
	flex-direction: column;
}

.signup-btn {
	width: fit-content;
	margin: 0 auto;
	background: var(--color-blue);
	color: white;
	border-radius: 20px;
}

.signup-btn:hover {
	color: white;
	background: #1921FF;
}

.form-group label {
	font-size: 0.9rem;
}

.signup-footer {
	font-size: 0.85rem;
}

.signup-footer a {
	text-decoration: none;
}

.signup-footer a:hover {
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
	<div class="container mt-5 signup-container">
		<h2 class="mb-4 text-center signup-header">Signup</h2>
		<form action="<%=request.getContextPath() + "/usercontroller"%>"
			method="post" id="signupForm" novalidate>
			<input type="hidden" name="action" value="register">
			<div class="form-group">
				<label for="name">Name</label> <input type="text"
					class="form-control" id="name" name="name" required>
				<div class="invalid-feedback error">Please enter your name.</div>
			</div>

			<div class="form-group">
				<label for="email">Email</label> <input type="email"
					class="form-control" id="email" name="email" required>
				<div class="invalid-feedback error">Please enter a valid email
					address.</div>
			</div>

			<div class="form-group">
				<label for="phone">Phone</label> <input type="tel"
					class="form-control" id="phone" name="phone" pattern="\d{10}"
					required>
				<div class="invalid-feedback error">Please enter a 10-digit
					phone number.</div>
			</div>

			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					class="form-control" id="password" name="password" minlength="6"
					required>
				<div class="invalid-feedback error">Password must be at least
					6 characters long.</div>
			</div>

			<div class="form-group">
				<label for="confirmPassword">Confirm Password</label> <input
					type="password" class="form-control" id="confirmPassword"
					name="confirmPassword" required>
				<div class="invalid-feedback error" id="confirmPasswordFeedback">Please
					confirm your password.</div>
			</div>

			<button type="submit" class="btn mt-3 signup-btn">Sign Up</button>

			<div class="divider mt-2">
				<div></div>
				<span>OR</span>
				<div></div>
			</div>
			<p class="text-center signup-footer">
				Already have an account ? <a href="<%=request.getContextPath()+"/user/SiginIn.jsp" %>">Login</a>
			</p>

		</form>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script>
		$(document).ready(function() {
			$('#signupForm').on('submit', function(event) {
				event.preventDefault();

				const password = $('#password').val();
				const confirmPassword = $('#confirmPassword').val();
				const confirmPasswordFeedback = $('#confirmPasswordFeedback');

				if (this.checkValidity() === false) {
					$(this).addClass('was-validated');
					return false;
				} else if (password !== confirmPassword) {
					confirmPasswordFeedback.text('Passwords do not match.');
					$('#confirmPassword').addClass('is-invalid');
					return false;
				} else {
					confirmPasswordFeedback.text('');
					$('#confirmPassword').removeClass('is-invalid');
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
	
</script>



</html>