package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.connection.ConnectionFactory;
import com.library.dto.User;
import com.library.exception.CustomError;

public class UserDAOImpl implements UserDAO {

	Connection connection = null;

	public UserDAOImpl() {
		connection = ConnectionFactory.getDBConnection();
	}

	@Override
	public boolean addUser(User user) throws CustomError {
		String insertQuery = "INSERT INTO USERS (name,email,password,phone) VALUES (?,?,?,?)";
		String fetchQuery = "SELECT * FROM USERS WHERE email=?";

		try {
			// Using try-with-resources for automatic resource management
			PreparedStatement fetchStatement = connection.prepareStatement(fetchQuery);
			fetchStatement.setString(1, user.getEmail());
			ResultSet resultSet = fetchStatement.executeQuery();
			if (resultSet.next()) {
				throw new CustomError("Email Already Exists");
			}

			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getPhone());

			int res = preparedStatement.executeUpdate();
			if (res != 1) {
				throw new CustomError("Registration Failed");
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public User getUser(String email, String password) throws CustomError, SQLException {
		String fetchQuery = "SELECT * FROM USERS WHERE EMAIL =?";
		
			PreparedStatement preparedStatement = connection.prepareStatement(fetchQuery);
			preparedStatement.setString(1, email);
			ResultSet mailExists = preparedStatement.executeQuery();
			if (!mailExists.next()) {
				throw new CustomError("Account Not Found");
			}

			if (!email.equals(mailExists.getString("email"))) {
				throw new CustomError("Email not exists");
			}

			if (!password.equals(mailExists.getString("password"))) {
				throw new CustomError("Incorrect Password");
			}
			
			User user = new User();
			user.setUserId(mailExists.getInt("user_id"));
			user.setName(mailExists.getString("name"));
			user.setEmail(email);
			user.setPhone(mailExists.getString("phone"));
			user.setJoinedAt(mailExists.getTimestamp("created_at"));
			user.setRole(mailExists.getString("role"));
		    user.setPassword(mailExists.getString("password"));
			return user;

	}

	@Override
	public User getUser(int user_id) {
		String fetchQuery = "SELECT * FROM USERS WHERE USER_ID =?";
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(fetchQuery);
			preparedStatement.setInt(1, user_id);
			ResultSet findUser = preparedStatement.executeQuery();
			
			if(findUser.next())
			{
				User user = new User();
				user.setUserId(findUser.getInt("user_id"));
				user.setName(findUser.getString("name"));
				user.setEmail(findUser.getString("email"));
				user.setPhone(findUser.getString("phone"));
				user.setJoinedAt(findUser.getTimestamp("created_at"));
				user.setRole(findUser.getString("role"));
			    user.setPassword(findUser.getString("password"));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteuser(int user_id) {
		// TODO Auto-generated method stub
		return false;
	}

}
