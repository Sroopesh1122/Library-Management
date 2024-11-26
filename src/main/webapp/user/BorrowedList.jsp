<%@page import="com.library.dto.Book"%>
<%@page import="com.library.dao.BookDaoImpl"%>
<%@page import="com.library.dao.UserBorrowBookDao"%>
<%@page import="com.library.dao.BorrowBookDaoImpl"%>
<%@page import="com.library.dao.BorrowBookDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.library.dto.BorrowBook"%>
<%@page import="com.library.dao.UserBorrowedBookDaoImpl"%>
<%@page import="com.library.dto.UserBorrowedBook"%>
<%@page import="java.util.List"%>
<%@page import="com.library.dao.UserDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Barrowed List</title>

<%@include file="/commonUtils/FontBootstrapCSS.jsp"%>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<%
User sessionUser = (User) session.getAttribute("user");
if(sessionUser==null)
{
	RequestDispatcher rd = request.getRequestDispatcher("/user/SiginIn.jsp");
	rd.forward(request, response);
}
if (sessionUser.getRole().equalsIgnoreCase("admin")) {
	request.setAttribute("menu", "Issued List");
} else {
	request.setAttribute("menu", "Borrowed List");
}
%>
<style type="text/css">
body {
	min-height: 100vh;
	width: 100%;
	font-family: "Outfit", sans-serif;
}

.wrapper {
	width: 100%;
	padding: 20px;
}

.not-book-found {
	width: 300px;
	min-height: 80vh;
	margin: 0 auto;
	display: flex;
	justify-content: center;
	align-items: center;
	display: flex;
}

.not-book-found .inner-wrapper {
	width: 650px;
	padding: 20px;
	border-radius: 10px;
	border: 2px solid #ededed;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
}

.not-book-found .inner-wrapper h1 {
	font-size: 1.2rem;
}

.not-book-found .inner-wrapper a {
	text-align: center;
	text-decoration: none;
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

.custom-table {
	padding: 5px;
	border: 1px solid gray;
	border-radius: 10px;
}

.custom-table .table-head {
	padding: 5px;
	display: flex;
	background: #f1f1f1;
}

.table-data .table-row {
	font-size: 0.95rem;
	padding: 5px;
	display: flex;
	border-bottom: 1px solid #f1f1f1;
}

.table-data .table-row:hover {
	background: #f1f1f1;
}

.table-head span:nth-child(1), .table-row span:nth-child(1) {
	width: 15%;
}

.table-head span:nth-child(2), .table-row span:nth-child(2) {
	width: 15%;
}

.table-head span:nth-child(3), .table-head span:nth-child(4), .table-row span:nth-child(3),
	.table-row span:nth-child(4) {
	width: 30%;
}

.table-head span:nth-child(5), .table-row span:nth-child(5) {
	width: 20%;
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
.returned{
 font-size: 0.7rem;
}

.text-red
{
 color: red;
 }
 .text-green{
  color: green;
 }

</style>
</head>
<body>
	<%@include file="/admin/NavBar.jsp"%>


	<%
	int pageNo = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
	int limit = request.getParameter("limit") != null ? Integer.parseInt(request.getParameter("limit")) : 10;
	List<UserBorrowedBook> borrowedIds = null;
	UserBorrowBookDao userBorrowBookDao = new UserBorrowedBookDaoImpl();
	if (user.getRole().equalsIgnoreCase("admin")) {
		borrowedIds = userBorrowBookDao.get(pageNo, limit);
	} else {
		borrowedIds = userBorrowBookDao.get(user.getUserId(), pageNo, limit);
	}

	List<BorrowBook> borrowedBooks = new ArrayList<BorrowBook>();
	int totalBooks = user.getRole().equalsIgnoreCase("admin") ? userBorrowBookDao.getTotalBorrowedBooks(pageNo, limit)
			: userBorrowBookDao.getTotalBorrowedBooks(user.getUserId(), pageNo, limit);

	BorrowBookDao borrowBookDao = new BorrowBookDaoImpl();

	for (UserBorrowedBook ub : borrowedIds) {
		BorrowBook bb = borrowBookDao.get(ub.getBorrow_id());
		if (bb != null) {
			borrowedBooks.add(bb);
		}
	}
	%>
	<%
	if (borrowedBooks.size() == 0) {
	%>

	<div class="not-book-found">

		<div class="inner-wrapper">
			<%
			if (user.getRole().equalsIgnoreCase("admin")) {
			%>
			<h1>No Records Found</h1>

			<%
			} else {
			%>
			<h1>No Borrowed Books Found</h1>
			<a href="<%=request.getContextPath() + "/books/getAllBooks"%>">Try</a>
			<%
			}
			%>
		</div>

	</div>


	<%
	} else {
	%>

	<div class="wrapper">
		<h3>
			Books Borrowed :
			<%=totalBooks%></h3>


		<div class="custom-table">

			<div class="table-head">
				<span> Borrowed Id </span> <span> Boook Id / Title </span> <span>
					Borrowed Date </span> <span> Due Date </span> 
					<%
					  if(user.getRole().equalsIgnoreCase("admin"))
					  {
						 %>
						  <span> Status </span>
						 <% 
					  }else{
						  %>
						  <span> Action </span>
						  <%
					  }
					%>
			</div>

			<div class="table-data mt-2">

				<%
				for (BorrowBook b : borrowedBooks) {
				%>
				<div class="table-row">
					<%
					Book book = new BookDaoImpl().getBookById(b.getBookId());
					%>
					<span> <%=b.getBorrowId()%>
					</span> <span><%=b.getBookId() + "/" + book.getTitle()%></span> <span>
						<%=b.getBorrowTimestamp().toLocaleString()%>
						
					</span> <span> <%=b.getDueTimestamp().toLocaleString()%>
					</span>


					<%
					if (!user.getRole().equalsIgnoreCase("admin")) {
						if (b.getReturnTimestamp() == null) {
					%>
					<span>
						<button type="button" class="custom-btn" data-toggle="modal" data-target="#exampleModal" onclick="handleReturnClick('<%=request.getContextPath()+"/books/return?borrowId="+b.getBorrowId()%>')">
                                         Return Book
                         </button>
   
					</span>
					<%
					} else {
					%>
					<span> <button disabled="disabled" class="custom-btn-plain" >Returned</button> <span class="returned">on <%=b.getReturnTimestamp().toLocaleString()%></span> </span>
					<%
					}

					} else {
					  if(b.getReturnTimestamp()!=null)
					  {
						  %>
						    <span class="returned text-green"> Returned on <%=b.getReturnTimestamp().toLocaleString() %> </span>
						  <% 
					  }else{
						  %>
						  <span class="returned text-red"> Not returned  </span>
						  <%
					  }
					}
					%>

				</div>
				<%
				}
				%>


			</div>
		</div>
	</div>
	<div class="pagination mt-2">
		<%
		int currentPage = pageNo;
		int startPage = Math.max(1, currentPage - 2);
		int noOfPages = (int) Math.ceil((double) totalBooks / limit);
		int endPage = Math.min(noOfPages, currentPage + 2);

		if (currentPage > 1) {
		%>
		<a
			href="<%=request.getContextPath() + "/book/Borrowist.jsp"%>?page=<%=currentPage - 1%>&limit=<%=limit%>"
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
			href="<%=request.getContextPath() + "/book/Borrowist.jsp"%>?page=<%=i%>&limit=<%=limit%>"
			class="pagination-btn"><%=i%></a>
		<%
		}
		}

		if (currentPage < noOfPages) {
		%>
		<a
			href="<%=request.getContextPath() + "/book/Borrowist.jsp"%>?page=<%=currentPage + 1%>&limit=<%=limit%>"
			class="pagination-btn">Next</a>
		<%
		}
		%>
	</div>

	<%
	}
	%>
	
	
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Confirm Return</h5>
        <button type="button" class="close btn " data-dismiss="modal" aria-label="Close" onclick="clearUrl()">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Are you sure want to return book?
      </div>
      <div class="modal-footer">
        <button type="button" class="custom-btn-plain" data-dismiss="modal" onclick="clearUrl()">Close</button>
        <button type="button" class="custom-btn" onclick="handleConfirmClick()" >Confirm</button>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script type="text/javascript">
	
	<%
	 if(session.getAttribute("message")!=null)
	 {
		 %>
		 Swal.fire({
	            title: 'Success',
	            text: "<%=session.getAttribute("message").toString()%>",
			icon : 'success',
		});
		 <%
		 session.removeAttribute("message");
	 }
	  
	%>
	
	
	
	
	 let url=""
	 
	 function clearUrl()
	 {
		 url =""
	 }
	 
	 function handleReturnClick(val)
	 {
		 url = val;
	 }
	 
	 function handleConfirmClick()
	 {
		 if(url)
		{
		 window.location.href = url
		}
	 }
	
	
	</script>
	
</body>
</html>