<%@page import="com.library.dto.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style>
.custom-navbar{
 background: white !important;
 padding: 20px;
}

.navbar-brand{
   font-size: 1.5rem;
   font-weight: 600;
   color:var(--color-blue) !important;
}
.navbar-toggler{
  border: none;
  color:var(--color-blue) !important;
}

.nav-link{
  color: black !important;
}  
   
.active-menu{
   color:var(--color-blue) !important;
}

.logout
{
  background: var(--color-blue);
  color: white;
  border-radius: 20px;
}
.logout:hover{
 color: white;
}
.custom-btn {
	padding: 4px 6px;
	border-radius: 8px;
	border: none;
	background: var(--color-blue);
	color: white;
	font-size: 0.8rem;
}
.custom-btn-plain {
	padding: 4px 6px;
	border-radius: 8px;
	border: 1px solid var(--color-blue);
	background:white;
	color: var(--color-blue);
	font-size: 0.8rem;
}

</style>  


<% User user =(User) session.getAttribute("user");
  if(user == null)
  {
	  response.sendRedirect(request.getContextPath()+"/user/SiginIn.jsp");
	  return;
  }
%>

  
  
<nav class="navbar navbar-expand-lg navbar-light bg-light custom-navbar">
  <div class="container-fluid">
    <a class="navbar-brand" href="">Library </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
     <i class="fa-solid fa-bars"></i>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <%
          if(user.getRole().equalsIgnoreCase("admin"))
          {
        	  %>
        	   <li class="nav-item">
                  <a class="nav-link " aria-current="page" href="<%= request.getContextPath()+"/admin/AdminMainPage.jsp"%>">Dashboard</a>
              </li>
              </li>
               
               	   <li class="nav-item">
                  <a class="nav-link " aria-current="page" href="<%= request.getContextPath()+"/user/BorrowedList.jsp"%>">Issued List</a>
               </li>
        	  <%
          }else{
        	  %>
        	   <li class="nav-item">
                  <a class="nav-link " aria-current="page" href="<%= request.getContextPath()+"/user/UserMainPage.jsp"%>">Home</a>
               </li>
               
               	   <li class="nav-item">
                  <a class="nav-link " aria-current="page" href="<%= request.getContextPath()+"/user/BorrowedList.jsp"%>">Borrowed List</a>
               </li>
        	  <% 
          }
           
        
        
        
        %>
        <li class="nav-item">
          <a class="nav-link" href="<%= request.getContextPath()+"/authors/Authors.jsp"%>">Authors</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%= request.getContextPath()+"/books/getAllBooks"%>">Books</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%= request.getContextPath()+"/categories/Categories.jsp"%>">Categories</a>
        </li>
        <li>
         <span class="btn logout" onclick="showLogoutMadol()" >Logout</span>
        </li>
      </ul>
    </div>
  </div>
  
</nav>

<div class="modal fade" id="logoutModal" tabindex="-1" aria-labelledby="logoutModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="logoutModalLabel">Confirm Logout</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        Are you sure you want to log out?
      </div>
      <div class="modal-footer">
        <button type="button" class="custom-btn-plain" data-bs-dismiss="modal">Cancel</button>
        <button type="button" class="custom-btn" onclick="handleConfirmLogout()">Logout</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" defer="defer">

    function showLogoutMadol()
    {
    	var myModal = new bootstrap.Modal(document.getElementById('logoutModal'));
    	myModal.show();
    }
    
    function handleConfirmLogout(){
    	window.location.href = '<%= request.getContextPath()+"/usercontroller"%>'
    	
    }

 

    $(document).ready(function(){
    	var path = window.location.pathname;
        var page = path.split("/").pop();
        
        var activeMenu = "<%=request.getAttribute("menu")%>" || "Dashboard";
        
        $(".navbar-nav .nav-link").each(function () {
            var href = $(this).attr("href");
            var menuItem =$(this).text()
            if (menuItem === activeMenu) {
              $(this).addClass("active-menu"); 
            } else {
              $(this).removeClass("active-menu"); 
            }
          });
        
    })
</script>