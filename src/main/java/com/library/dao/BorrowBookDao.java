package com.library.dao;

import java.util.List;

import com.library.dto.BorrowBook;
import com.library.exception.CustomError;

public interface BorrowBookDao
{
  public BorrowBook add (BorrowBook b) throws CustomError;
  public BorrowBook update(BorrowBook b) throws CustomError;
  public BorrowBook get(int borrow_id) throws CustomError;
  public List<BorrowBook> get() throws CustomError;  
}
