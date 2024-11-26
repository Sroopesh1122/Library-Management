<%@page import="java.sql.Date"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.library.dao.CategoryDaoImpl"%>
<%@page import="com.library.dto.Book"%>
<%@page import="com.library.dao.BookDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("menu", "Borrowed List");
   int bookId = request.getParameter("bookId")!=null ? Integer.parseInt(request.getParameter("bookId")) :-1;
   
   Book findBook = new BookDaoImpl().getBookById(bookId);
   if(findBook ==null)
   {
	   RequestDispatcher rd = request.getRequestDispatcher("/CustomError.jsp");
	   request.setAttribute("errorMsg", "Book Not Found");
	   rd.forward(request, response);
   }
   findBook.setCategories(new CategoryDaoImpl().getAllCategories(findBook.getBookId()));


%>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><%=findBook.getTitle() %></title>
<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>

<style type="text/css">
body {
	min-height: 100vh;
	width: 100%;
	font-family: "Outfit", sans-serif;
}
.center{
  display: flex;
  justify-content: center;
  align-items: center;
}

.outer-wrapper{
  width: 100%;
  height: 85vh;
}
.outer-wrapper div{
 gap:10px;
}
.img-wrapper{
 width: 350px;
 height: 500px;
}
.img-wrapper img{
  width: 100%;
  height: 100%;
  border-radius: 20px;
}
.details-wrapper{
  width: 400px;
}

.book-details {
   background: #f7f7f7;
   border-radius: 10px;
   padding: 10px;
   
}
.confirmation{
 background: #f7f7f7;
 
   border-radius: 10px;
   padding: 10px;
}
.confirmation h4{
  font-size: 1.1rem;
  
}
.confirmation p{
  font-size: 0.8rem;
  color: red;
}
.confirmation div{
 display: flex;
 justify-content: space-around;
 align-items: center;
}
.confirmation div button{
	padding: 3px 6px;
	border-radius: 8px;
	font-size: 0.98rem;
	border: 1px solid black;
}
.confirmation div button:first-child{
 background: white;
}
.confirmation div button:last-child {
	background: green;
	color: white;
	border: none;
}

@media (max-width: 800px) {
    .outer-wrapper div {
        flex-direction: column;
    }
}

</style>
</head>
<body>
<%@include file="/admin/NavBar.jsp"%>

<div class="outer-wrapper center">
    
     <div class="center">
        <div  class="img-wrapper">
           <img alt="<%=findBook.getTitle() %>" src="<%=findBook.getImg() %>">
        </div>
     <div class="details-wrapper">
         <div class="book-details">
              <h1><%=findBook.getTitle() %></h1>
              <div>
                <span>Book Id : </span>
                <span><%=findBook.getBookId() %></span>
              </div> 
              <div>
                <span>Category : </span>
                <span><%=findBook.getAllCategoriesName() %></span>
              </div> 
              <div>
                <span>Borrowed By : </span>
                <span><%=user.getName() %></span>
              </div> 
              <hr class="mt-1">
              <div>
                <span>Borrowing Date : </span>
                <span id="borrowing-date"></span>
              </div>
              <div>
                <span>Return Date : </span>
                <span id="return-date"></span>
                <span class="ms-1 badge bg-danger">7 days</span>
              </div>      
         </div>
         <div class="confirmation mt-2">
            <h4>Confirm Borrow</h4>
             <p>Note : Book must to return on or before return date.</p>
             <div>
                 <button type="button" onclick="goBack()" >Back</button>
                  <button type="button" <%=findBook.getAvailable() == 0? "disabled='disabled'":""  %> onclick="handleConfirmClick()" >Confirm</button>
                
             </div>
         </div>
     </div>
        
        
     </div>
     
</div>


<script type="text/javascript">

function addDaysToDate(days) {
    const currentDate = new Date(); // Get the current date
    currentDate.setDate(currentDate.getDate() + days); // Add the number of days
    return currentDate;
}

function customDateFormat(date) {
    const day = String(date.getDate()).padStart(2, '0'); // Get the day (2 digits)
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Get the month (2 digits, months are 0-indexed)
    const year = date.getFullYear(); // Get the year (4 digits)

    return `${day}/${month}/${year}`;
}



const newDate = addDaysToDate(5); // Add 5 days to the current date
console.log(newDate); // Outputs the updated date

$(document).ready(function(){
   console.log(customDateFormat(new Date()))
   console.log(customDateFormat(addDaysToDate(7)))
	$("#borrowing-date").text(customDateFormat(new Date()));
	$("#return-date").text(customDateFormat(addDaysToDate(7)));
})

function goBack()
{
 window.history.back();	
}

function handleConfirmClick()
{
 window.location.href = '<%=request.getContextPath()+"/books/borrow?bookId="+findBook.getBookId()+"&userId="+user.getUserId()+"&days="+7%>'
}




</script>


</body>
</html>