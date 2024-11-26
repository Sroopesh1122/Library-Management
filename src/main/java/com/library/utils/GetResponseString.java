package com.library.utils;

import java.io.BufferedReader;
import java.io.IOException;


public class GetResponseString 
{
    public static String getJsonString(BufferedReader Data)
    {
    	StringBuilder jsonBuffer = new StringBuilder();
		String line;
		try(BufferedReader reader = Data){
			while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonBuffer.toString();	
    }
}
