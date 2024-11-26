package com.library.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.library.exception.CustomError;


public class CloudinaryUploader
{
	public static  String upload(String path) throws CustomError
	{
		Cloudinary cloudinary = CloudinaryConfiguration.getCloudinaryInstance();
	    
	    //absolute path
	    File file = new File(path); // Replace with the path to your image
	   
	    //relative path
	     
	    Map<String, Object> uploadParams = ObjectUtils.asMap(
	            "folder", "image" // Specify the folder here
	        );
	    
	    try {
	        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
	       
	        return (String ) uploadResult.get("url");
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new CustomError("Upload Failed");
	    }
	}
}
