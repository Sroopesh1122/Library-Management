package com.library.dao;

import java.util.List;

import com.library.dto.Category;
import com.library.exception.CustomError;

public interface CategoryDao
{
  public Category addCategory(Category category)  throws CustomError;
  public Category getCategory(int id)  throws CustomError;
  public boolean deleteCategory(int id)  throws CustomError;
  public Category updateCategory(Category c)  throws CustomError ;
  public List<Category> getAllCategories(String text,int page,int limit)  throws CustomError;
  public List<Category> getAllCategories(int book_id)  throws CustomError;
  public int  getTotalCategories (String text);
  
}
