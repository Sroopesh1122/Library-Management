package com.library.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Book {
	private int bookId;
	private String title;
	private String publisher;
	private int publishedYear;
	private int available = 1;
	private Timestamp createdAt;
	private String img ;
	private List<Author> authors;
	private List<Category>  categories;
	
	public Book() {
		super();
		this.authors= new ArrayList<Author>();
		this.categories = new ArrayList<Category>();
	}

	public Book(int bookId, String title, String publisher, int publishedYear,int available, Timestamp createdAt,
			List<Author> authors, List<Category> categories) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.publisher = publisher;
		this.publishedYear = publishedYear;
		this.available = available;
		this.createdAt = createdAt;
		this.authors = authors;
		this.categories = categories;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

	public int isAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getAvailable() {
		return available;
	}
	
	
	public String getAllAuthorsName()
	{
		StringBuilder authorsName = new StringBuilder();
        if(authors.size()==0)
        {
        	return null;
        }
        for(int i=0;i<authors.size();i++)
        {
        	Author a= authors.get(i);
        	if(a.getName()!=null)
        	{
        		if(i==0)
        		{
        			authorsName.append(a.getName());
        		}else {
        			authorsName.append(", "+a.getName());
        		}
        	}
        }
		
		return authorsName.toString();
	}
	
	public String getAllCategoriesName ()
	{
		StringBuilder categoriesName = new StringBuilder();
        if(categories.size()==0)
        {
        	return null;
        }
        for(int i=0;i<categories.size();i++)
        {
        	Category c= categories.get(i);
        	if(c.getName()!=null)
        	{
        		if(i==0)
        		{
        			categoriesName.append(c.getName());
        		}else {
        			categoriesName.append(", "+c.getName());
        		}
        	}
        }
		
		return categoriesName.toString();
	}
	

	@Override
	public String toString() {
		return "\nBook [bookId=" + bookId + ", title=" + title + ", publisher=" + publisher + ", publishedYear="
				+ publishedYear + ", available=" + available + ", createdAt=" + createdAt + ", img=" + img
				+ ", authors=" + authors + ", categories=" + categories + "]";
	}
	
	public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";  // Return empty JSON in case of error
        }
    }
	



	
	
	

}
