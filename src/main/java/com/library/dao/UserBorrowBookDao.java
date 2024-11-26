package com.library.dao;

import java.util.List;

import com.library.dto.UserBorrowedBook;
import com.library.exception.CustomError;

public interface UserBorrowBookDao
{
  public UserBorrowedBook add (UserBorrowedBook b) throws CustomError;
  public UserBorrowedBook update (UserBorrowedBook b) throws CustomError;
  public List<UserBorrowedBook> get(int user_id ,int page,int limit) throws CustomError;
  public List<UserBorrowedBook> get(int page,int limit) throws CustomError;
  public int getTotalBorrowedBooks(int page,int limit) throws CustomError;
  public int getTotalBorrowedBooks(int user_id ,int page,int limit) throws CustomError;
 
  
}
