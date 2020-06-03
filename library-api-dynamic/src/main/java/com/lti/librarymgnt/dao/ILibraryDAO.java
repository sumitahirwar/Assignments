package com.lti.librarymgnt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.librarymgnt.dto.Book;



@Repository
public interface ILibraryDAO extends JpaRepository<Book,Integer>{ 

}
