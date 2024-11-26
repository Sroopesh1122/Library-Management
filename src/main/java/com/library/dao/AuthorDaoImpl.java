package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.connection.ConnectionFactory;
import com.library.dto.Author;
import com.library.exception.CustomError;

public class AuthorDaoImpl implements AuthorDao {
	Connection conn;

	public AuthorDaoImpl() {
		conn = ConnectionFactory.getDBConnection();
	}

	@Override
	public Author addAuthor(Author a) {
		// Check If already exists
			String query = "INSERT INTO AUTHORS (NAME) VALUES (?)";
			try {
				PreparedStatement stm = conn.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
				stm.setString(1, a.getName());
				int res = stm.executeUpdate();
				if (res > 0) {
					ResultSet generatedKeys = stm.getGeneratedKeys();
					if(generatedKeys.next())
					{
						a.setAuthorId(generatedKeys.getInt(1));
					}
					return a;

				} else {
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}

	@Override
	public Author updateAuthor(Author a) throws CustomError {
		String findAuthor = "SELECT * FROM AUTHORS WHERE AUTHOR_ID = ?";
		try {
			PreparedStatement findStm = conn.prepareStatement(findAuthor);
			findStm.setInt(1, a.getAuthorId());
			ResultSet resultSet = findStm.executeQuery();
			if (resultSet.next()) {
				String updateQuery = "UPDATE AUTHORS SET NAME = ? WHERE AUTHOR_ID = ?";
				PreparedStatement updateStm = conn.prepareStatement(updateQuery);
				updateStm.setString(1, a.getName());
				updateStm.setInt(2, a.getAuthorId());
				if (updateStm.executeUpdate() > 0) {
					return a;
				} else {
					return null;
				}
			} else {
				throw new CustomError("Author Not Found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteAuthor(int id) {
		String findAuthor = "SELECT * FROM AUTHORS WHERE AUTHOR_ID = ?";
		try {
			PreparedStatement findStm = conn.prepareStatement(findAuthor);
			findStm.setInt(1, id);
			ResultSet resultSet = findStm.executeQuery();
			if (resultSet.next()) {
				String deleteQuery = "DELETE FROM AUTHORS WHERE AUTHOR_ID=?";
				PreparedStatement stm = conn.prepareStatement(deleteQuery);
				stm.setInt(1, id);
				int res = stm.executeUpdate();
				if (res > 0) {
					return true;
				} else {
					return false;
				}

			} else {
				throw new CustomError("Author Not Found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public List<Author> getAuthors() {
		String query = "SELECT * FROM AUTHORS";
		List<Author> authors = new ArrayList<Author>();
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			ResultSet resultSet = stm.executeQuery();
			while (resultSet.next()) {
				Author a = new Author();
				a.setAuthorId(resultSet.getInt(1));
				a.setName(resultSet.getString(2));
				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return authors;
	}

	@Override
	public Author getAuthor(int id) {
		String query = "SELECT * FROM AUTHORS WHERE AUTHOR_ID=?";
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setInt(1, id);
			ResultSet resultSet = stm.executeQuery();
			if (resultSet.next()) {
				Author a = new Author();
				a.setAuthorId(resultSet.getInt(1));
				a.setName(resultSet.getString(2));
				return a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Author> getAuthors(int page,int limit) {
		int skip = (page-1)*limit;
		String query = "SELECT * FROM AUTHORS LIMIT ? OFFSET ?";
		List<Author> authors = new ArrayList<Author>();
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setInt(1, limit);
			stm.setInt(2, skip);
			ResultSet resultSet = stm.executeQuery();
			while (resultSet.next()) {
				Author a = new Author();
				a.setAuthorId(resultSet.getInt(1));
				a.setName(resultSet.getString(2));
				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return authors;
	}
	
	@Override
	public List<Author> getAuthors(String seachText,int page,int limit) {
		int skip = (page-1)*limit;
		String query = "SELECT * FROM AUTHORS WHERE NAME LIKE ? LIMIT ? OFFSET ?";
		List<Author> authors = new ArrayList<Author>();
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setString(1, "%"+seachText+"%");
			stm.setInt(2, limit);
			stm.setInt(3, skip);
			ResultSet resultSet = stm.executeQuery();
			while (resultSet.next()) {
				Author a = new Author();
				a.setAuthorId(resultSet.getInt(1));
				a.setName(resultSet.getString(2));
				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return authors;
	}

	@Override
	public List<Author> getAuthors(int book_id) {
		String query = "SELECT A.* FROM AUTHORS A JOIN BOOK_AUTHORS BA ON BA.AUTHOR_ID = A.AUTHOR_ID WHERE BA.BOOK_ID = ?";
		List<Author> authors = new ArrayList<Author>();
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setInt(1, book_id);
			ResultSet resultSet = stm.executeQuery();
			while (resultSet.next()) {
				Author a = new Author();
				a.setAuthorId(resultSet.getInt(1));
				a.setName(resultSet.getString(2));
				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}
	
	@Override
	public int getTotal() {
		String query="SELECT COUNT(*) AS TOTAL FROM AUTHORS";
		try {
			PreparedStatement stm= conn.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
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
	
	@Override
	public int getTotal(String seachText) {
		String query="SELECT COUNT(*) AS TOTAL FROM AUTHORS WHERE NAME LIKE ?";
		try {
			PreparedStatement stm= conn.prepareStatement(query);
			stm.setString(1, "%"+seachText+"%");
			ResultSet rs = stm.executeQuery();
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
