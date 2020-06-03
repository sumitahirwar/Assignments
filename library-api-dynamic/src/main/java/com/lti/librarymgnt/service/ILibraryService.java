package com.lti.librarymgnt.service;

import java.util.List;

import com.lti.librarymgnt.dto.Book;

public interface ILibraryService {

	List<Book> getAllBooks();
	Book getBookById(int bookId);
	void addBook(Book book);
	void updateBook(Book book, int bookId);
	void deleteBook(int bookId);
	
}
