package com.library.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.dao.Book_CategoryDao;
import com.library.dao.Book_CategoryImpl;
import com.library.dao.CategoryDao;
import com.library.dao.CategoryDaoImpl;
import com.library.dto.Category;

@WebServlet(urlPatterns = "/category")
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryDao categoryDao= new CategoryDaoImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("id")!=null)
		{
			int id = Integer.parseInt(request.getParameter("id"));
			RequestDispatcher requestDispatcher= request.getRequestDispatcher("/categories/Categories.jsp");
			Book_CategoryDao book_CategoryDao= new Book_CategoryImpl();
			book_CategoryDao.deleteBookCategory(id);
			
			
			if(categoryDao.deleteCategory(id))
			{
				 request.setAttribute("success","Category deleted Sucessfully"); 
			}else {
				 request.setAttribute("error","Failed to delete category");  	
			}
			requestDispatcher.forward(request, response); 
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       if(request.getParameter("categoryName")!=null)
       {
    	   String name = request.getParameter("categoryName").trim();
    	   
    	   Category c= new Category();
    	   c.setName(name);
    	   Category resultCat =  categoryDao.addCategory(c);
    	   RequestDispatcher requestDispatcher= request.getRequestDispatcher("/categories/Categories.jsp");
    	   if(resultCat!=null)
    	   {
    		 request.setAttribute("success","Category Added");   
    	   }else {
    		   request.setAttribute("error","Failed to add category");    
    	   }
    	   requestDispatcher.forward(request, response); 
       }
		
	}

}
