package com.library.dao;

import java.util.List;

import com.library.dto.Book;
import com.library.exception.CustomError;

public interface BookDao 
{
   public Book registerBook(Book book) throws CustomError ;
   public Book getBookById(int id) throws CustomError;
   public Book updateBook (Book b) throws CustomError;
   public List<Book> getBooks(int page,int limit,String category,String text) throws CustomError;
   public boolean deleteBook (int id) throws CustomError;
   public int getTotalBooks(String text,String category) throws CustomError;
//   public int getTotalBooksByCategory(String filter_category ,String text) throws CustomError;
   public List<Book> getBookByAuthorId(int id);
   public int getTotalBooks();
}
