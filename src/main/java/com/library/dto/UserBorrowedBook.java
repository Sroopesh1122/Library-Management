package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserBorrowedBook {
	private int record_id;
	private int user_id;
	private int borrow_id;
	

	public UserBorrowedBook() {
		super();
	}
	
	
	public UserBorrowedBook(int record_id, int user_id, int borrow_id) {
		super();
		this.record_id = record_id;
		this.user_id = user_id;
		this.borrow_id = borrow_id;
	}
	
	
	
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getBorrow_id() {
		return borrow_id;
	}
	public void setBorrow_id(int borrow_id) {
		this.borrow_id = borrow_id;
	}
	
	
	
	
	@Override
	public String toString() {
		return "UserBorrowedBook [record_id=" + record_id + ", user_id=" + user_id + ", borrow_id=" + borrow_id + "]";
	}
	
	
	
	
	
}
