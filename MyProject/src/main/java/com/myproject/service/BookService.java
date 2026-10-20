package com.myproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.entity.Book;
import com.myproject.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	// 책 한권을 DB에 저장 요청 처리
	@Transactional
	public Book saveBook(String title, String author, String status) {
		Book book = new Book(title, author, status);
		
		return bookRepository.save(book);
	}
}
