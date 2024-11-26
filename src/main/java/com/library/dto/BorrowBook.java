package com.library.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BorrowBook {
	private int borrowId;
	private int userId;
	private int bookId;
	private Timestamp borrowTimestamp;
	private Timestamp dueTimestamp;
	private Timestamp returnTimestamp;

	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public Timestamp getBorrowTimestamp() {
		return borrowTimestamp;
	}

	public void setBorrowTimestamp(Timestamp borrowTimestamp) {
		this.borrowTimestamp = borrowTimestamp;
	}

	public Timestamp getDueTimestamp() {
		return dueTimestamp;
	}

	public void setDueTimestamp(Timestamp dueTimestamp) {
		this.dueTimestamp = dueTimestamp;
	}

	public Timestamp getReturnTimestamp() {
		return returnTimestamp;
	}

	public void setReturnTimestamp(Timestamp returnTimestamp) {
		this.returnTimestamp = returnTimestamp;
	}

	@Override
	public String toString() {
		return "BorrowBook [borrowId=" + borrowId + ", userId=" + userId + ", bookId=" + bookId + ", borrowTimestamp="
				+ borrowTimestamp + ", dueTimestamp=" + dueTimestamp + ", returnTimestamp=" + returnTimestamp + "]";
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
