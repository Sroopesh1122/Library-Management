package com.library.dao;

import java.util.List;
import com.library.dto.BookCategory;

public interface Book_CategoryDao 
{
	 public BookCategory addBookCategory(BookCategory ba);
	  public BookCategory updateBookCategory(BookCategory ba);
	  public boolean deleteBookCategory (int id);
	  public List<BookCategory> getCategories ();
	  public List<BookCategory> getCategories (int book_id);
}
