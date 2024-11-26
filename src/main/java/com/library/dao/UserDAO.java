package com.library.dao;

import java.sql.SQLException;
import com.library.dto.User;
import com.library.exception.CustomError;

public interface UserDAO
{
  public boolean addUser(User user) throws CustomError,SQLException ;
  public User getUser (String email,String password) throws CustomError ,SQLException;
  public User getUser (int user_id);
  public User updateUser (User user);
  public boolean deleteuser (int user_id);
  
}
