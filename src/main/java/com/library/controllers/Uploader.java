package com.library.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.library.utils.CloudinaryUploader;

@WebServlet(urlPatterns = "/img/upload")
@MultipartConfig
public class Uploader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Uploader() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part part = request.getPart("img-file");
		System.out.println(request.getParameter("title"));
        String filename = part.getSubmittedFileName();

        // Get the absolute path of the images folder (relative to the deployed application)
        String uploadPath = getServletContext().getRealPath("/imgs");
        System.out.println("Upload path: " + uploadPath); // Debugging: Log the path

        // Ensure the directory exists
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            boolean dirsCreated = uploadDir.mkdirs();
            System.out.println("Directory created: " + dirsCreated); // Debugging: Check if directory was created
        }

        // Define the full file path
        String filePath = uploadPath + File.separator + filename;
        
        // Write the uploaded file to disk
        part.write(filePath);

        // Log file existence
        File file = new File(filePath);
        System.out.println("File exists: " + file.exists());
        System.out.println("File saved at: " + filePath);
        
       System.out.println( CloudinaryUploader.upload(filePath));
        file.delete();
        // Respond to the client
        response.getWriter().println("File uploaded successfully to: " + filePath);
        
		
	}

}
