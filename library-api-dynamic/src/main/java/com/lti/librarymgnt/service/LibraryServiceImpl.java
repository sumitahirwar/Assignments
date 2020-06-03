package com.lti.librarymgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lti.librarymgnt.dao.ILibraryDAO;
import com.lti.librarymgnt.dto.Book;

@Service
public class LibraryServiceImpl implements ILibraryService {

	
	
	@Autowired
	private ILibraryDAO daoRef;

	@Override
	public List<Book> getAllBooks() {
		
		return daoRef.findAll();
	}

	@Override
	public Book getBookById(int bookId) {
		
		return	daoRef.getOne(bookId);
	}

	@Override
	public void addBook(Book book) {
		daoRef.save(book);
	}

	@Override
	public void updateBook(Book book, int bookId) {
		daoRef.save(book);
		
	}

	@Override
	public void deleteBook(int bookId) {
		daoRef.deleteById(bookId);
	}
	
	
}
