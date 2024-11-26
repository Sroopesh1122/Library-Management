package com.library.dao;

import java.util.List;

import com.library.dto.BookAuthor;
import com.library.exception.CustomError;

public interface Book_AuthorsDao 
{ 
	  public BookAuthor addBookAuthor(BookAuthor ba) throws CustomError;
	  public BookAuthor updateBookAuthor(BookAuthor ba);
	  public boolean deleteBookAuthor (int id);
	  public List<BookAuthor> getAuthors ();
	  public List<BookAuthor> getAuthors (int book_id);
	  
	  
}
