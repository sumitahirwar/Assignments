package com.lti.librarymgnt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.lti.librarymgnt.dto.Book;
import com.lti.librarymgnt.service.ILibraryService;

@RestController
public class LibraryController {

	@Autowired
	ILibraryService bookServiceRef;

	@RequestMapping(value = "/books")
	public List<Book> getAllBooks() {
		return bookServiceRef.getAllBooks();
		}
		
		@RequestMapping("/books/{bookId}")
		public Book getBookById(@PathVariable int bookId) {
			return bookServiceRef.getBookById(bookId);
		}
		
		@RequestMapping(method = RequestMethod.POST,value="/books")
		public void addBook(@RequestBody Book book) {
			 bookServiceRef.addBook(book);
			
		}
		
		@RequestMapping(method = RequestMethod.PUT,value="/books/{bookId}")
		public void updateBook(@RequestBody Book book,@PathVariable int bookId) {
			bookServiceRef.updateBook(book,bookId);
			
		}
		
		@RequestMapping(method = RequestMethod.DELETE,value="/books/{bookId}")
		public void deleteBook(@PathVariable int bookId) {
			 bookServiceRef.deleteBook(bookId);
			
		}
		
	}

