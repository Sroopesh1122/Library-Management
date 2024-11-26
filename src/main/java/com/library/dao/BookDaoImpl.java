package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.library.connection.ConnectionFactory;
import com.library.dto.Book;
import com.library.exception.CustomError;

public class BookDaoImpl implements BookDao {

	Connection connection = null;
	String totalResultQuery=null;

	public BookDaoImpl() {
		this.connection = ConnectionFactory.getDBConnection();
	}

	@Override
	public Book registerBook(Book book) throws CustomError {
		String insertQuery = "INSERT INTO BOOKS (TITLE,PUBLISHER,PUBLISHED_YEAR,AVAILABLE,IMG,CREATED_AT) VALUES (?,?,?,?,?,now())";
		try {
			PreparedStatement insertStm = connection.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
			insertStm.setString(1, book.getTitle());
			insertStm.setString(2, book.getPublisher());
			insertStm.setInt(3, book.getPublishedYear());
			insertStm.setInt(4, 1);
			insertStm.setString(5, book.getImg());
			if(insertStm.executeUpdate() > 0)
			{
				ResultSet generatedKeys = insertStm.getGeneratedKeys();
				if(generatedKeys.next())
				{
					book.setBookId(generatedKeys.getInt(1));
				}
				return book;
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
	public Book getBookById(int id) throws CustomError{
		String query = "SELECT * FROM BOOKS WHERE BOOK_ID=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet= preparedStatement.executeQuery();
			if(resultSet.next())
			{
			   Book b= new Book();
			   b.setBookId(resultSet.getInt(1));
			   b.setTitle(resultSet.getString(2));
			   b.setPublisher(resultSet.getString(3));
			   b.setPublishedYear(resultSet.getInt(4));
			   b.setAvailable(resultSet.getInt(5));
			   b.setCreatedAt(resultSet.getTimestamp(6));
			   b.setImg(resultSet.getString(7));
			   return b;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	
	
	@Override
	public List<Book> getBooks(int page, int limit, String category, String text) throws CustomError {
		
		String query="SELECT";
		List<Book> books= new ArrayList<Book>();
		
		int skip = (page- 1)*limit;
		
		if(text !=null && category !=null)
		{
			query = "select distinct filtered_books.* from  (select b.* from books b where b.title like ? ) as filtered_books join book_categories bc on bc.book_id = filtered_books.book_id join categories c on c.category_id =  bc.category_id where c.name = ? ";
		}
		
		else if(text !=null)
		{
			query="select distinct filtered_books.* from  (select b.* from books b where b.title like ? ) as filtered_books join book_categories bc on bc.book_id = filtered_books.book_id join categories c on c.category_id =  bc.category_id ";
		}
		else if(category!=null)
		{
			query="select distinct filtered_books.* from  (select b.* from books b  ) as filtered_books join book_categories bc on bc.book_id = filtered_books.book_id join categories c on c.category_id = bc.category_id where c.name=?";
		}
		else {
			query = "SELECT * FROM BOOKS ";
		}
		
		this.totalResultQuery = "select count(*) from  ("+query+") as data ";
		
		query = query + " ORDER BY CREATED_AT DESC LIMIT ? OFFSET ?";
		
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			int index=1;
			if(text!=null && category!=null)
			{
				preparedStatement.setString(index++, "%"+text+"%");
				preparedStatement.setString(index++, category);
			}
			else if (text !=null) {
				preparedStatement.setString(index++, "%"+text+"%");
			}
			else if (category!=null) {
				preparedStatement.setString(index++, category);
			}
			preparedStatement.setInt(index++, limit);
			preparedStatement.setInt(index++,skip);
			System.out.println(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			AuthorDao authorDao= new AuthorDaoImpl();
			CategoryDao categoryDao= new CategoryDaoImpl(); 
			while(resultSet.next())
			{
				Book b= new Book();
				b.setBookId(resultSet.getInt(1));
				b.setTitle(resultSet.getString(2));
				b.setPublisher(resultSet.getString(3));
				b.setPublishedYear(resultSet.getInt(4));
				b.setAvailable(resultSet.getInt(5));
				b.setCreatedAt(resultSet.getTimestamp(6));
				b.setImg(resultSet.getString(7));
				b.setAuthors(authorDao.getAuthors(b.getBookId()));
				b.setCategories(categoryDao.getAllCategories(b.getBookId()));
				books.add(b);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return books;
	}

	@Override
	public boolean deleteBook(int id) throws CustomError {
		String deleteQuery="DELETE FROM BOOKS WHERE BOOK_ID=?";
		try {
			PreparedStatement deleteStm = connection.prepareStatement(deleteQuery);
			deleteStm.setInt(1, id);
			if(deleteStm.executeUpdate() > 0)
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
	public int getTotalBooks(String text,String category) throws CustomError {
		
		if(totalResultQuery!=null)
		{
		   try {
			PreparedStatement preparedStatement = connection.prepareStatement(totalResultQuery);
			int index=1;
			if(text!=null && category!=null)
			{
				preparedStatement.setString(index++, "%"+text+"%");
				preparedStatement.setString(index++, category);
			}
			else if (text !=null) {
				preparedStatement.setString(index++, "%"+text+"%");
			}
			else if (category!=null) {
				preparedStatement.setString(index++, category);
			}
			ResultSet res = preparedStatement.executeQuery();
			if(res.next())
			{
				return res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		   
		}
		
		return 0;
	}
	
	@Override
	public Book updateBook(Book b) throws CustomError {
	    
		Book findBook = getBookById(b.getBookId());
		if(findBook == null)
		{
			throw new CustomError("Book not found");
		}
		
		String updateQuery = "UPDATE BOOKS SET TITLE=? ,PUBLISHER=?,PUBLISHED_YEAR=?,AVAILABLE=?,IMG=? WHERE BOOK_ID=?";
		try {
			PreparedStatement updateStm = connection.prepareStatement(updateQuery);
			updateStm.setString(1, b.getTitle());
			updateStm.setString(2, b.getPublisher());
			updateStm.setInt(3, b.getPublishedYear());
			updateStm.setInt(4, b.getAvailable());
			updateStm.setString(5,b.getImg());
			updateStm.setInt(6, b.getBookId());
			if(updateStm.executeUpdate() > 0)
			{
				return b;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomError("Update Failed");
		}
		
	}
	
	
	@Override
	public List<Book> getBookByAuthorId(int id) {
		String query = "select b.* from books b join book_authors ba on b.book_id = ba.book_id where ba.author_id =?";
		List<Book> books=new ArrayList<Book>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet= preparedStatement.executeQuery();
			while(resultSet.next())
			{
			   Book b= new Book();
			   b.setBookId(resultSet.getInt(1));
			   b.setTitle(resultSet.getString(2));
			   b.setPublisher(resultSet.getString(3));
			   b.setPublishedYear(resultSet.getInt(4));
			   b.setAvailable(resultSet.getInt(5));
			   b.setCreatedAt(resultSet.getTimestamp(6));
			   b.setImg(resultSet.getString(7));
			   books.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return books;
	}
	
	@Override
	public int getTotalBooks() {
		String query="SELECT COUNT(*) FROM BOOKS";
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				return rs.getInt(1);
			}else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	
	


}
