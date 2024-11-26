package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.connection.ConnectionFactory;
import com.library.dto.Category;
import com.library.exception.CustomError;

public class CategoryDaoImpl implements CategoryDao
{
	Connection conn;
	public CategoryDaoImpl() {
		conn = ConnectionFactory.getDBConnection();
	}

	@Override
	public Category addCategory(Category category) throws CustomError {
		String insertQuery = "INSERT INTO CATEGORIES (NAME) VALUES (?)";
		PreparedStatement insertStm;
		try {
			insertStm = conn.prepareStatement(insertQuery);
			insertStm.setString(1, category.getName());
			if(insertStm.executeUpdate() > 0)
			{
				return category;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomError("Something wnet wrong");
		}
		
	}

	@Override
	public Category getCategory(int id) {
		String query ="SELECT * FROM CATEGORIES WHERE CATEGORY_ID =?";
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setInt(1, id);
			ResultSet resultSet = stm.executeQuery();			
			if (resultSet.next()) {
				Category c= new Category();
				c.setCategoryId(resultSet.getInt(1));
				c.setName(resultSet.getString(2));
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteCategory(int id) {
		String deleteQuery = "DELETE FROM CATEGORIES WHERE CATEGORY_ID=?";
		try {
			PreparedStatement deleteStm = conn.prepareStatement(deleteQuery);
			deleteStm.setInt(1, id);
			if(deleteStm.executeUpdate()>0)
			{
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Category> getAllCategories(String text ,int page,int limit) {
		
		String query =null;
		PreparedStatement stm=null;
		List<Category> categories= new ArrayList<Category>();
		
		try {
			if(text == null)
			{
				query = "SELECT * FROM CATEGORIES ";
				
 			}else {
 				query = "SELECT * FROM CATEGORIES WHERE NAME LIKE ? ";
 			}
			
			if(page >0 && limit >0)
			{
				int skip = (page - 1) * limit;
				query =query + " LIMIT ? OFFSET ?";
				stm = conn.prepareStatement(query);
				if(text == null)
				{
					stm.setInt(2, skip);
					stm.setInt(1, page*limit);
				}else {
					stm.setString(1, "%"+text+"%");
					stm.setInt(3, skip);
					stm.setInt(2, page*limit);
				}
			}else {
				stm = conn.prepareStatement(query);
			}
			ResultSet resultSet = stm.executeQuery();
			while (resultSet.next()) {
				Category c= new Category();
				c.setCategoryId(resultSet.getInt(1));
				c.setName(resultSet.getString(2));
				categories.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return categories;
	}
	
	@Override
	public List<Category> getAllCategories(int book_id) {
		String query ="SELECT C.* FROM CATEGORIES C JOIN BOOK_CATEGORIES BC ON BC.CATEGORY_ID = C.CATEGORY_ID WHERE BC.BOOK_ID =?";
		List<Category> categories= new ArrayList<Category>();
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setInt(1, book_id);
			ResultSet resultSet = stm.executeQuery();
			while (resultSet.next()) {
				Category c= new Category();
				c.setCategoryId(resultSet.getInt(1));
				c.setName(resultSet.getString(2));
				categories.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return categories;
	}
	
	@Override
	public Category updateCategory(Category c) {
		 if(getCategory(c.getCategoryId())==null)
		 {
			 throw new CustomError("Category not found!");
		 }
		 
		 String updateQuery  = "UPDATE CATEGORIES SET NAME=? WHERE CATEGORY_ID=?";
		 try {
			PreparedStatement updateStrm = conn.prepareStatement(updateQuery);
			updateStrm.setString(1, c.getName());
			updateStrm.setInt(2, c.getCategoryId());
			if(updateStrm.executeUpdate() >0)
			{
				return c;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomError("Update Failed");
			
		}
	}
	 
	@Override
	public int getTotalCategories(String text) {
		String fetchQuery=null;
		PreparedStatement stm = null;
		try {
			if(text ==null)
			{
				fetchQuery="SELECT COUNT(*) as total FROM CATEGORIES";
				stm = conn.prepareStatement(fetchQuery);
				
			}else {
				fetchQuery="SELECT COUNT(*)as total FROM CATEGORIES WHERE NAME LIKE ?";	
				stm = conn.prepareStatement(fetchQuery);
				stm.setString(1, "%"+text+"%");
			}
			
			ResultSet resultSet = stm.executeQuery();
			if(resultSet.next())
			{
				return resultSet.getInt(1);
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
 
}
