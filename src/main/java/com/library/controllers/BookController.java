package com.library.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.dao.AuthorDao;
import com.library.dao.AuthorDaoImpl;
import com.library.dao.BookDao;
import com.library.dao.BookDaoImpl;
import com.library.dao.Book_AuthorsImpl;
import com.library.dao.Book_CategoryDao;
import com.library.dao.Book_CategoryImpl;
import com.library.dao.BorrowBookDao;
import com.library.dao.BorrowBookDaoImpl;
import com.library.dao.CategoryDao;
import com.library.dao.CategoryDaoImpl;
import com.library.dao.UserBorrowBookDao;
import com.library.dao.UserBorrowedBookDaoImpl;
import com.library.dao.UserDAOImpl;
import com.library.dto.Author;
import com.library.dto.Book;
import com.library.dto.BookAuthor;
import com.library.dto.BookCategory;
import com.library.dto.BorrowBook;
import com.library.dto.Category;
import com.library.dto.User;
import com.library.dto.UserBorrowedBook;
import com.library.exception.CustomError;
import com.library.utils.CloudinaryUploader;

@WebServlet(urlPatterns = "/books/*")
@MultipartConfig
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookDaoImpl bk = new BookDaoImpl();
	RequestDispatcher requestDispatcher = null;

	public BookController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();

		switch (path) {
		case "/getAllBooks":
			getAllBooks(request, response);
			break;
		case "/getBook":
			getBook(request, response);
			break;
		case "/borrow":
			borrowBook(request, response);
			break;
		case "/return":
			returnBook(request, response);
			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		if ("/register".equalsIgnoreCase(path)) {
			addBook(request, response);
		} else if ("/update".equalsIgnoreCase(path)) {
			updateBook(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	// Services methods

	public void getAllBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		int limit = request.getParameter("limit") != null ? Integer.parseInt(request.getParameter("limit")) : 10;
		String category = request.getParameter("category");
		String searchText = request.getParameter("q");
		List<Book> books = bk.getBooks(page, limit, category, searchText);
		int totalBooks = bk.getTotalBooks(searchText, category);
		request.setAttribute("totalBooks", totalBooks);

		CategoryDao categoryDao = new CategoryDaoImpl();
		List<Category> categories = categoryDao.getAllCategories(null, 0, 0);
		request.setAttribute("books", books);
		request.setAttribute("menu", "Books");
		request.setAttribute("page", page);
		request.setAttribute("limit", limit);
		request.setAttribute("selectedCategory", category);
		request.setAttribute("searchText", searchText);
		request.setAttribute("categories", categories);
		requestDispatcher = request.getRequestDispatcher("/admin/BooksPage.jsp");
		requestDispatcher.forward(request, response);
	}

	public void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("id") != null ? request.getParameter("id")
				: (String) request.getAttribute("id");
		if (bookId == null) {
			requestDispatcher = request.getRequestDispatcher("/CustomError.jsp");
			request.setAttribute("errorMsg", "Page Not Found");
			requestDispatcher.forward(request, response);
			return;
		}
		int id = Integer.parseInt(bookId);
		AuthorDao authorDao = new AuthorDaoImpl();
		CategoryDao categoryDao2 = new CategoryDaoImpl();
		List<Author> authors = authorDao.getAuthors();
		Book book = bk.getBookById(id);
		book.setAuthors(authorDao.getAuthors(book.getBookId()));
		book.setCategories(categoryDao2.getAllCategories(book.getBookId()));
		request.setAttribute("authors", authors);
		CategoryDao categoryDao3 = new CategoryDaoImpl();
		List<Category> categories3 = categoryDao3.getAllCategories(null, 0, 0);
		request.setAttribute("categories", categories3);
		request.setAttribute("book", book);
		requestDispatcher = request.getRequestDispatcher("/book/Book.jsp");
		requestDispatcher.forward(request, response);

	}

	private void borrowBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = request.getParameter("bookId") != null ? Integer.parseInt(request.getParameter("bookId")) : -1;
		int userId = request.getParameter("userId") != null ? Integer.parseInt(request.getParameter("userId")) : -1;
		int days = request.getParameter("days") != null ? Integer.parseInt(request.getParameter("days")) : 0;
		RequestDispatcher rd = request.getRequestDispatcher("/book/BorrowBook.jsp");
		try {
			Book findBook = bk.getBookById(bookId);
			User findUser = new UserDAOImpl().getUser(userId);
			if (findBook == null) {
				throw new CustomError("Book Not Found");
			}
			if (findBook.getAvailable() == 0) {
				throw new CustomError("Not Avalibale to borrow");
			}
			if (findUser == null) {
				throw new CustomError("User not Found");
			}

			BorrowBook bb = new BorrowBook();
			bb.setBookId(bookId);
			bb.setUserId(userId);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			Calendar cal = Calendar.getInstance();
			cal.setTime(ts);
			cal.add(Calendar.DAY_OF_MONTH, days);
			Timestamp newTs = new Timestamp(cal.getTimeInMillis());
			bb.setDueTimestamp(newTs);
			BorrowBookDao borrowBookDao = new BorrowBookDaoImpl();
			bb = borrowBookDao.add(bb);
			if (bb != null) {
				UserBorrowBookDao userBorrowBookDao = new UserBorrowedBookDaoImpl();
				UserBorrowedBook ubb = new UserBorrowedBook();
				ubb.setBorrow_id(bb.getBorrowId());
				ubb.setUser_id(userId);
				ubb = userBorrowBookDao.add(ubb);
				if (ubb != null) {
					findBook.setAvailable(0);
					findBook = bk.updateBook(findBook);
					if (findBook != null) {
						HttpSession session = request.getSession();
						session.setAttribute("borrowed_success", true);
						response.sendRedirect(request.getContextPath() + "/books/getBook?id=" + bookId);
					} else {
						throw new CustomError("Failed to borrow Book");
					}

				} else {
					throw new CustomError("Failed to borrow Book");
				}

			} else {
				throw new CustomError("Failed to borrow Book");
			}

		} catch (CustomError e) {
			e.printStackTrace();
			request.setAttribute("error", true);
			request.setAttribute("message", e.getMessage());
			rd.forward(request, response);
		}
	}

	private void returnBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int borrowId = request.getParameter("borrowId") != null ? Integer.parseInt(request.getParameter("borrowId"))
				: -1;
		RequestDispatcher rd = null;
		try {
			if (borrowId == -1) {
				throw new CustomError("Invalid Borrow Id");
			}
			
			BorrowBookDao borrowBookDao = new BorrowBookDaoImpl();
			BorrowBook bb=  borrowBookDao.get(borrowId);
			if(bb==null)
			{
				throw new CustomError("Record not found");
			}
			bb.setReturnTimestamp(new Timestamp(System.currentTimeMillis()));
			bb = borrowBookDao.update(bb);
			if(bb == null)
			{
				throw new CustomError("Somthing Went Wrong");
			}
			Book returningBook = bk.getBookById(bb.getBookId());
			returningBook.setAvailable(1);
			returningBook  =  bk.updateBook(returningBook);
			if(returningBook==null)
			{
				throw new CustomError("Something Went Wrong");
			}
			HttpSession session =  request.getSession();
			session.setAttribute("message","Book Returned Successfully!!");
			response.sendRedirect(request.getContextPath()+"/user/BorrowedList.jsp");
			
			

		} catch (CustomError e) {
			rd = request.getRequestDispatcher("/user/BorrowedList.jsp");
			request.setAttribute("error", true);
			request.setAttribute("message", e.getMessage());
			rd.forward(request, response);
		}

	}

	public void updateBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonData = request.getParameter("formData") != null ? request.getParameter("formData") : "{}";
		System.out.println(jsonData);
		ObjectMapper mapper = new ObjectMapper();
		Book b = mapper.readValue(jsonData, Book.class);
		if (bk.updateBook(b) != null) {
			AuthorDao authorDao = new AuthorDaoImpl();
			authorDao.updateAuthor(b.getAuthors().get(0));
			Book_CategoryDao book_CategoryDao = new Book_CategoryImpl();
			book_CategoryDao.deleteBookCategory(b.getBookId());
			for (Category c : b.getCategories()) {
				BookCategory ba = new BookCategory();
				ba.setBook_id(b.getBookId());
				ba.setCategory_id(c.getCategoryId());
				book_CategoryDao.addBookCategory(ba);
			}

			request.setAttribute("message", "Book Updated Sucessfully");

		} else {
			request.setAttribute("message", "Updated failed");
		}

		request.setAttribute("id", b.getBookId() + "");
		getBook(request, response);

	}

	public void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String title = request.getParameter("title");
		String publisher = request.getParameter("publisher");
		String year = request.getParameter("year");
		String author = request.getParameter("authors");

		if (title == null || publisher == null || year == null || author == null) {
			return;
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/book/AddBook.jsp");
		int yearNumer = Integer.parseInt(year);

		Part part = request.getPart("img");

		// Uploading

		try {
			String filename = part.getSubmittedFileName();
			String uploadPath = getServletContext().getRealPath("/imgs");

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

			String uploadedUrl = CloudinaryUploader.upload(filePath);

			if (uploadedUrl != null) {
				Book b = new Book();
				b.setTitle(title);
				b.setPublisher(publisher);
				b.setPublishedYear(yearNumer);
				b.setImg(uploadedUrl);
				b.setAvailable(1);

				BookDao bookDao = new BookDaoImpl();
				b = bookDao.registerBook(b);
				if (b != null) {
					Author newAuthor = new Author();
					newAuthor.setName(author);
					newAuthor = new AuthorDaoImpl().addAuthor(newAuthor);
					if (newAuthor != null) {
						BookAuthor ba = new BookAuthor();
						ba.setAuthor_id(newAuthor.getAuthorId());
						ba.setBook_id(b.getBookId());
						new Book_AuthorsImpl().addBookAuthor(ba);
					} else {
						throw new CustomError("Registration Failed");
					}
					List<Category> categories = new ObjectMapper().readValue(request.getParameter("categories"),
							new TypeReference<List<Category>>() {
							});

					for (Category c : categories) {
						BookCategory bc = new BookCategory();
						bc.setBook_id(b.getBookId());
						bc.setCategory_id(c.getCategoryId());
						new Book_CategoryImpl().addBookCategory(bc);
					}
					request.setAttribute("message", "Book Registered");
					requestDispatcher.forward(request, response);

				} else {
					throw new CustomError("Registration Failed");
				}
			}
		} catch (CustomError e) {
			request.setAttribute("error", true);
			request.setAttribute("message", e.getMessage());
			requestDispatcher.forward(request, response);
		}

	}

}
