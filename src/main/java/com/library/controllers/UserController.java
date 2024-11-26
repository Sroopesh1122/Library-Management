package com.library.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.dao.UserDAOImpl;
import com.library.dto.User;
import com.library.exception.CustomError;

@WebServlet(urlPatterns = "/usercontroller")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public UserController() {
        super();
    }
    
   
    
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
   	request.getSession().removeAttribute("user");
   	request.getSession().invalidate();
   	response.sendRedirect(request.getContextPath()+"/user/SiginIn.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action  = request.getParameter("action");
		if(action.equals("register")) {
			registerUser(request, response);
		}
		else if(action.equalsIgnoreCase("login"))
		{
			loginUser(request,response);
		}
	
	}
	
	

	private void loginUser(HttpServletRequest request, HttpServletResponse response)  {
		request.removeAttribute("errorMsg");
		UserDAOImpl userDAOImpl = new  UserDAOImpl();
		RequestDispatcher requestDispatcher = null;
		try {
			try {
				User user = userDAOImpl.getUser(request.getParameter("email"), request.getParameter("password"));
			    HttpSession session = request.getSession();
			    session.setAttribute("user", user);
			    if(user.getRole().equalsIgnoreCase("admin"))
			    {
			    	response.sendRedirect(request.getContextPath()+"/admin/AdminMainPage.jsp");
			    	return ;
			    }else {
			    	requestDispatcher = request.getRequestDispatcher("/user/UserMainPage.jsp"); 
			    }
			    requestDispatcher.forward(request, response);
			    
			} catch (CustomError e) {
				request.setAttribute("errorMsg",e.getMessage());
				requestDispatcher = request.getRequestDispatcher("/user/SiginIn.jsp");
			    requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				request.setAttribute("errorMsg","Something Went Wrong");
				requestDispatcher = request.getRequestDispatcher("/CustomError.jsp");
			    requestDispatcher.forward(request, response);
			}
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		
	}


	private void registerUser (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.removeAttribute("errorMsg");
		User user = new  User();
		user.setName(request.getParameter("name"));
		user.setEmail(request.getParameter("email"));
		user.setPhone(request.getParameter("phone"));
		user.setPassword(request.getParameter("password"));
		RequestDispatcher requestDispatcher = null;
		UserDAOImpl userDAOImpl = new  UserDAOImpl();
		try {
			userDAOImpl.addUser(user);
		    requestDispatcher = request.getRequestDispatcher("/success.jsp");
		    requestDispatcher.forward(request, response);
		} catch (CustomError e) {
			request.setAttribute("errorMsg",e.getMessage());
			requestDispatcher = request.getRequestDispatcher("/user/SignUp.jsp");
		    requestDispatcher.forward(request, response);
		}catch(IOException e)
		{
			request.setAttribute("errorMsg","Something Went Wrong!");
			requestDispatcher = request.getRequestDispatcher("/user/SignUp.jsp");
		    requestDispatcher.forward(request, response);
		}
	}
	
	
	

}
