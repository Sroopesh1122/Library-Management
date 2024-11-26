package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.library.connection.ConnectionFactory;
import com.library.dto.BookCategory;

public class Book_CategoryImpl implements Book_CategoryDao {
	Connection connection;

	public Book_CategoryImpl() {
		connection = ConnectionFactory.getDBConnection();
	}

	@Override
	public BookCategory addBookCategory(BookCategory ba) {
		String inertQuery = "INSERT INTO BOOK_CATEGORIES (BOOK_ID,CATEGORY_ID) VALUES (?,?)";
		try {
			PreparedStatement inertStm = connection.prepareStatement(inertQuery);
			inertStm.setInt(1, ba.getBook_id());
			inertStm.setInt(2, ba.getCategory_id());
			if(inertStm.executeUpdate() > 0)
			{
				return ba;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BookCategory updateBookCategory(BookCategory ba) {
		String updateQuery= "UPDATE BOOK_CATEGORIES SET CATEGORY_ID=? WHERE BOOK_ID=? AND CATEGORY_ID=?";
		try {
			PreparedStatement updateStm = connection.prepareStatement(updateQuery);
			updateStm.setInt(1, ba.getCategory_id());
			updateStm.setInt(2, ba.getBook_id());
			updateStm.setInt(3, ba.getCategory_id());
			if(updateStm.executeUpdate()>0)
			{
				return ba;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteBookCategory(int id) {
		String deleteQuery = "DELETE FROM BOOK_CATEGORIES WHERE CATEGORY_ID=?";
		try {
			PreparedStatement deleteStm = connection.prepareStatement(deleteQuery);
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
	public List<BookCategory> getCategories() {
		String query="SELECT * FROM BOOK_CATEGORIES";
		List<BookCategory> BookCategories= new ArrayList<BookCategory>();
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			ResultSet resultSet= preparedStatement.executeQuery();
			while(resultSet.next())
			{
				BookCategory bc= new BookCategory();
				bc.setBook_id(resultSet.getInt(1));
				bc.setCategory_id(resultSet.getInt(2));;
				BookCategories.add(bc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return BookCategories;
	}

	@Override
	public List<BookCategory> getCategories(int book_id) {
		String query="SELECT * FROM BOOK_CATEGORIES";
		List<BookCategory> BookCategories= new ArrayList<BookCategory>();
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setInt(1, book_id);
			ResultSet resultSet= preparedStatement.executeQuery();
			while(resultSet.next())
			{
				BookCategory bc= new BookCategory();
				bc.setBook_id(resultSet.getInt(1));
				bc.setCategory_id(resultSet.getInt(2));;
				BookCategories.add(bc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return BookCategories;
	}

}
