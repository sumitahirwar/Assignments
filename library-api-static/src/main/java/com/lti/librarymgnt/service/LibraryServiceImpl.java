package com.lti.librarymgnt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lti.librarymgnt.dto.Book;

@Service
public class LibraryServiceImpl implements ILibraryService {

	List<Book>bookList = new ArrayList <> (Arrays.asList(new Book(1, "Head First Design Pattern", "Freeman & Freeman"), new Book(2, "Head First Java", "Freeman & Freeman"),
			new Book(3, "Head First JSP & Servlets", "Freeman & Freeman")));
	
	@Override
	public List<Book> getAllBooks() {
		return bookList;
	}

	@Override
	public Book getBookById(int bookId) {
		return bookList.stream().filter(e->e.getBookId()==bookId).findFirst().get();
	}

	@Override
	public void addBook(Book book) {
		bookList.add(book);
		
	}

	@Override
	public void updateBook(Book book, int bookId) {
		for(int i =0;i<bookList.size();i++) {
			Book e  = bookList.get(i);
			if(e.getBookId()==bookId) {
				bookList.set(i, book);
				return;
			}
		}
		
	}

	@Override
	public void deleteBook(int bookId) {
		bookList.removeIf(e->e.getBookId()==bookId);
		
	}

}
