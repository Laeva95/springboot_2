package com.myproject.service;

import java.util.List;
import java.util.NoSuchElementException;

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
	
	// db의 모든 책 조회
	@Transactional(readOnly = true)
	public List<Book> findAll(){
		return bookRepository.findAll();
	}
	
	// status를 통해 특정 책들을 검색하는 메소드
	@Transactional(readOnly = true)
	public List<Book> findByStatus(String status) {
	    return bookRepository.findByStatus(status);
	}
	
	// id를 통해 특정 책 하나를 검색하는 메소드
	@Transactional(readOnly = true)
	public Book findBook(Long id) {
		// id를 통해 db에서 책 한권을 검색해서 가져옴
	    return bookRepository.findById(id).orElseThrow(() ->
	                    new NoSuchElementException("책을 찾을 수 없습니다. ID: " + id));
	}

	// 책의 데이터를 수정하는 메소드
	@Transactional
	public void updateBook(
	        Long id, String title, String author, String status) {

		// id를 통해 db에서 책 한권을 검색해서 가져옴
	    Book book = bookRepository.findById(id).orElseThrow(() ->
	                    new NoSuchElementException("책을 찾을 수 없습니다. ID: " + id));

	    // 가져온 책의 제목, 저자, 상태를 변경
	    book.update(title, author, status);
	}
	
	// 책 하나의 데이터를 삭제하는 메소드
	@Transactional
	public void deleteBook(Long id) {
		// id를 통해 책 한권을 검색해서 가져옴
	    Book book = bookRepository.findById(id).orElseThrow(() ->
	                    new NoSuchElementException("책을 찾을 수 없습니다. ID: " + id));

	    // 가져온 책 삭제
	    bookRepository.delete(book);
	}
	
	// 매개변수 문자열이 포함된 title을 검색해서 조회하는 메소드
	@Transactional(readOnly = true)
	public List<Book> searchByTitle(String keyword) {
	    return bookRepository.findByTitleContainingIgnoreCase(keyword);
	}

	// 매개변수 문자열이 포함된 author을 검색해서 조회하는 메소드
	@Transactional(readOnly = true)
	public List<Book> searchByAuthor(String keyword) {
	    return bookRepository.findByAuthorContainingIgnoreCase(keyword);
	}
}
