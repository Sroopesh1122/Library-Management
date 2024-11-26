package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.connection.ConnectionFactory;
import com.library.dto.BorrowBook;
import com.library.exception.CustomError;

public class BorrowBookDaoImpl implements BorrowBookDao 
{
	Connection connection=null;
   	
	public BorrowBookDaoImpl() {
		connection =  ConnectionFactory.getDBConnection();
	}
	

	@Override
	public BorrowBook add(BorrowBook b) throws CustomError {
		String query = "INSERT INTO BORROW_BOOKS (USER_ID,BOOK_ID,DUE_TIMESTAMP) VALUES (?,?,?)";
		try {
			PreparedStatement insertStm = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			insertStm.setInt(1, b.getUserId());
			insertStm.setInt(2, b.getBookId());
			insertStm.setTimestamp(3, b.getDueTimestamp());
			if(insertStm.executeUpdate() > 0)
			{
				ResultSet rs = insertStm.getGeneratedKeys();
				if(rs.next())
				{
					b.setBorrowId(rs.getInt(1));
					return b;
				}
			}else {
				throw new CustomError("Failed to add");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CustomError("Something Went Wrong");
		}
		return null;
	}

	@Override
	public BorrowBook update(BorrowBook b) throws CustomError {
		String query = "UPDATE BORROW_BOOKS SET DUE_TIMESTAMP = ? ,RETURN_TIMESTAMP=? WHERE BORROW_ID=?";
		try {
			PreparedStatement updatetStm = connection.prepareStatement(query);
			updatetStm.setTimestamp(1,b.getDueTimestamp());
			updatetStm.setTimestamp(2,b.getReturnTimestamp());
			updatetStm.setInt(3, b.getBorrowId());
			if(updatetStm.executeUpdate() > 0)
			{
				return b;
			}else {
				return null;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CustomError("Something Went Wrong");
		}
		
	}

	@Override
	public BorrowBook get(int borrow_id) throws CustomError {
		String query = "SELECT * FROM BORROW_BOOKS WHERE BORROW_ID = ?";
		try {
			PreparedStatement getStm = connection.prepareStatement(query);
			getStm.setInt(1, borrow_id);
			ResultSet rs = getStm.executeQuery();
			if(rs.next())
			{
			  BorrowBook b=  new BorrowBook();
			  b.setBorrowId(borrow_id);
			  b.setUserId(rs.getInt(2));
			  b.setBookId(rs.getInt(3));
			  b.setBorrowTimestamp(rs.getTimestamp(4));
			  b.setDueTimestamp(rs.getTimestamp(5));
			  b.setReturnTimestamp(rs.getTimestamp(6));
			  return b;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CustomError("Something Went Wrong");
		}
		return null;
	}

	@Override
	public List<BorrowBook> get() throws CustomError {
		String query = "SELECT * FROM BORROW_BOOKS ";
		List<BorrowBook> borrowedBooks=new ArrayList<BorrowBook>();
		try {
			PreparedStatement getStm = connection.prepareStatement(query);
			ResultSet rs = getStm.executeQuery();
			while(rs.next())
			{
			  BorrowBook b=  new BorrowBook();
			  b.setBookId(rs.getInt(1));
			  b.setUserId(rs.getInt(2));
			  b.setBookId(rs.getInt(3));
			  b.setBorrowTimestamp(rs.getTimestamp(4));
			  b.setDueTimestamp(rs.getTimestamp(5));
			  b.setReturnTimestamp(rs.getTimestamp(6));
			  borrowedBooks.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CustomError("Something Went Wrong");
		}
		return borrowedBooks;
	}

}
