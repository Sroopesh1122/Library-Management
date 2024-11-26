package com.library.dao;

import java.util.List;

import com.library.dto.Author;
import com.library.exception.CustomError;

public interface AuthorDao
{
  public Author addAuthor(Author a) throws CustomError;
  public Author updateAuthor(Author a) throws CustomError;
  public boolean deleteAuthor(int id) throws CustomError;
  public Author getAuthor(int id) throws CustomError;
  public List<Author> getAuthors ();
  public List<Author> getAuthors(int page,int limit);
  public List<Author> getAuthors(String seachText,int page,int limit);
  public List<Author> getAuthors(int book_id);
  public int getTotal();
  public int getTotal(String seachText);
  
}
