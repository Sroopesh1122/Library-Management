<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>
</head>

<style>
    body{
     min-height: 100vh;
     width: 100%;
     display: flex;
     justify-content: center;
     align-items: center;
     font-family: "Outfit", sans-serif;
    }
</style>
<%  String message =(String) request.getAttribute("errorMsg");
if(message == null)
{
	message="Something Went Wrong";
}
 %>
<body>
     <h1><%=message %></h1>
</body>
</html>