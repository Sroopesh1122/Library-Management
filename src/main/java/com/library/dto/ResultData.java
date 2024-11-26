package com.library.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResultData {
	private boolean error = false;
	private boolean success=true;
	private String errorMsg;
	private Object result;

	public ResultData(boolean error, boolean success,String errorMsg, Object result) {
		super();
		this.error = error;
		this.success= success;
		this.errorMsg = errorMsg;
		this.result = result;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean getSuccess() {
		return this.success;
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
