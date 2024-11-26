package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.connection.ConnectionFactory;
import com.library.dto.UserBorrowedBook;
import com.library.exception.CustomError;

public class UserBorrowedBookDaoImpl implements UserBorrowBookDao
{
	Connection connection =null;
	
	public UserBorrowedBookDaoImpl() {
		connection = ConnectionFactory.getDBConnection();
	}

	@Override
	public UserBorrowedBook add(UserBorrowedBook b) throws CustomError {
		String query="INSERT INTO USER_BORROWED_BOOKS (USER_ID,BORROW_ID) VALUES (?,?)";
		try {
			PreparedStatement insertStm = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            
			insertStm.setInt(1,b.getUser_id());
			insertStm.setInt(2, b.getBorrow_id());
			if(insertStm.executeUpdate() > 0)
			{
				ResultSet rs= insertStm.getGeneratedKeys();
				if(rs.next())
				{
					b.setRecord_id(rs.getInt(1));
					return b;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CustomError("Something Went Wrong");
		}
		return null;
	}

	@Override
	public UserBorrowedBook update(UserBorrowedBook b) throws CustomError {
		
		return null;
	}

	@Override
	public List<UserBorrowedBook> get(int user_id ,int page,int limit) throws CustomError {
		String query= "SELECT * FROM USER_BORROWED_BOOKS WHERE USER_ID=? ORDER BY RECORD_ID DESC LIMIT ? OFFSET ?";
		List<UserBorrowedBook> borrowedList = new ArrayList<UserBorrowedBook>();
		try {
			PreparedStatement getStm= connection.prepareStatement(query);
			getStm.setInt(1, user_id);
			getStm.setInt(2, limit);
			getStm.setInt(3, (page-1)*limit);
			
			ResultSet rs = getStm.executeQuery();
			while(rs.next())
			{
				UserBorrowedBook userBorrowedBook = new UserBorrowedBook();
				userBorrowedBook.setRecord_id(rs.getInt(1));
				userBorrowedBook.setUser_id(rs.getInt(2));
				userBorrowedBook.setBorrow_id(rs.getInt(3));
				borrowedList.add(userBorrowedBook);
				
			}
			return borrowedList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserBorrowedBook> get(int page,int limit) throws CustomError {
		String query= "SELECT * FROM USER_BORROWED_BOOKS ORDER BY RECORD_ID DESC LIMIT ? OFFSET ? ";
		List<UserBorrowedBook> borrowedList = new ArrayList<UserBorrowedBook>();
		try {
			PreparedStatement getStm= connection.prepareStatement(query);
			getStm.setInt(1, limit);
			getStm.setInt(2, (page-1)*limit);
			ResultSet rs = getStm.executeQuery();
			while(rs.next())
			{
				UserBorrowedBook userBorrowedBook = new UserBorrowedBook();
				userBorrowedBook.setRecord_id(rs.getInt(1));
				userBorrowedBook.setUser_id(rs.getInt(2));
				userBorrowedBook.setBorrow_id(rs.getInt(3));
				borrowedList.add(userBorrowedBook);
				
			}
			return borrowedList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public int getTotalBorrowedBooks(int page, int limit) throws CustomError {
		String query= "SELECT COUNT(*) FROM USER_BORROWED_BOOKS";
		List<UserBorrowedBook> borrowedList = new ArrayList<UserBorrowedBook>();
		try {
			PreparedStatement getStm= connection.prepareStatement(query);
			ResultSet rs = getStm.executeQuery();
			if(rs.next())
			{
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int getTotalBorrowedBooks(int user_id, int page, int limit) throws CustomError {
		String query= "SELECT COUNT(*) FROM USER_BORROWED_BOOKS WHERE USER_ID=?";
		try {
			PreparedStatement getStm= connection.prepareStatement(query);
			getStm.setInt(1, user_id);	
			ResultSet rs = getStm.executeQuery();
			if(rs.next())
			{
				return rs.getInt(1);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
  
}
