package com.library.servies;

import java.sql.SQLException;
import com.library.dao.UserDAOImpl;
import com.library.dto.User;
import com.library.exception.CustomError;

public class UserServices 
{
	UserDAOImpl userDAOImpl ;
	public UserServices() {
		this.userDAOImpl =  new UserDAOImpl();
	}
	
	public boolean registerUser (User user){
		return userDAOImpl.addUser(user);
	}
	
	public User loginUser (String email,String password) throws CustomError, SQLException {
		return userDAOImpl.getUser(email, password);
	}
	
	public User getUser(int id)
	{
		return userDAOImpl.getUser(id);
	}
	
	public boolean deleteUser(int id)
	{
		return userDAOImpl.deleteuser(id);
	}
}
