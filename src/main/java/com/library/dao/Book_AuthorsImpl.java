package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.connection.ConnectionFactory;
import com.library.dto.BookAuthor;
import com.library.exception.CustomError;

public class Book_AuthorsImpl implements Book_AuthorsDao {
	
	Connection conn;
	
	public Book_AuthorsImpl() {
		conn = ConnectionFactory.getDBConnection();
	}

	@Override
	public BookAuthor addBookAuthor(BookAuthor ba) {
		String insertQuery = "INSERT INTO BOOK_AUTHORS (BOOK_ID,AUTHOR_ID) VALUES (?,?)";
		String findQuery = "SELECT * FROM BOOK_AUTHORS WHERE BOOK_ID=? AND AUTHOR_ID=?";
		try {
			PreparedStatement findStm = conn.prepareStatement(findQuery);
			findStm.setInt(1, ba.getBook_id());
			findStm.setInt(2, ba.getAuthor_id());
			if(!findStm.executeQuery().next())
			{
				PreparedStatement insertStm = conn.prepareStatement(insertQuery);
				insertStm.setInt(1, ba.getBook_id());
				insertStm.setInt(2, ba.getAuthor_id());
				if(insertStm.executeUpdate() > 0)
				{
					return ba;
				}else {
					return null;
				}
			}else {
				throw new CustomError("Data already exists");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BookAuthor updateBookAuthor(BookAuthor ba) {
		return null;
	}

	@Override
	public boolean deleteBookAuthor(int id) {
		String deleteQuery = "DELETE FROM BOOK_AUTHORS WHERE BOOK_ID=?";
		String findQuery = "SELECT * FROM BOOK_AUTHORS WHERE BOOK_ID = ?";
		try {
			PreparedStatement findStm = conn.prepareStatement(findQuery);
			findStm.setInt(1,id );
			if(!findStm.executeQuery().next())
			{
				PreparedStatement deleteStm = conn.prepareStatement(deleteQuery);
				deleteStm.setInt(1,id) ;
				if(deleteStm.executeUpdate() > 0)
				{
					return true;
				}else {
					return false;
				}
			}else {
				throw new CustomError("Data already exists");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	

	@Override
	public List<BookAuthor> getAuthors() {
		String query="SELECT * FROM BOOK_AUTHORS";
		List<BookAuthor> bookAuthors= new ArrayList<BookAuthor>();
		try {
			PreparedStatement preparedStatement= conn.prepareStatement(query);
			ResultSet resultSet= preparedStatement.executeQuery();
			while(resultSet.next())
			{
				BookAuthor ba= new BookAuthor();
				ba.setBook_id(resultSet.getInt(1));
				ba.setAuthor_id(resultSet.getInt(2));
				bookAuthors.add(ba);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookAuthors;
	}

	@Override
	public List<BookAuthor> getAuthors(int book_id) {
		String query="SELECT * FROM BOOK_AUTHORS WHERE BOOK_ID=?";
		List<BookAuthor> bookAuthors= new ArrayList<BookAuthor>();
		try {
			PreparedStatement preparedStatement= conn.prepareStatement(query);
			preparedStatement.setInt(1, book_id);
			ResultSet resultSet= preparedStatement.executeQuery();
			while(resultSet.next())
			{
				BookAuthor ba= new BookAuthor();
				ba.setBook_id(resultSet.getInt(1));
				ba.setAuthor_id(resultSet.getInt(2));
				bookAuthors.add(ba);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return bookAuthors;
	}
	
	

}
