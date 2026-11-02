package com.myproject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.dto.Yes24BookResponse;
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
	public Book saveBook(
			String title, 
			String author, 
			String status,
			LocalDate startDate,
			LocalDate completedDate) {
		Book book = new Book(title, author, status, startDate, completedDate);
		
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
	        Long id, 
	        String title, 
	        String author, 
	        String status,
	        LocalDate startDate,
	        LocalDate completedDate) {

		// id를 통해 db에서 책 한권을 검색해서 가져옴
	    Book book = bookRepository.findById(id).orElseThrow(() ->
	                    new NoSuchElementException("책을 찾을 수 없습니다. ID: " + id));

	    // 가져온 책의 제목, 저자, 상태를 변경
	    book.update(title, author, status, startDate, completedDate);
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
	
	// 책의 상태, 검색어를 통해 책을 검색하는 메소드
	@Transactional(readOnly = true)
	public Page<Book> searchBooks(String status, String keyword, Pageable pageable) {
	    return bookRepository.searchBooks(status, keyword, pageable);
	}
	
	// YES24 api id를 이용해 책 등록 유무 확인
	@Transactional
	public Book saveBookFromYes24(
	        Yes24BookResponse.BookItem item) {

	    // 1. 예외처리
	    if (item == null || item.itemId() == null) {
	        throw new IllegalArgumentException(
	                "유효한 YES24 도서 정보가 없습니다."
	        );
	    }
	    
	    if (bookRepository.existsByYes24ItemId(item.itemId())) {
	        throw new IllegalArgumentException(
	                "이미 내 서재에 등록된 책입니다."
	        );
	    }
	    

	    // 2. 새 Book 엔티티 생성
	    Book book = new Book(
	            item.title(),
	            item.author(),
	            "PLANNED",
	            item.publisher(),
	            item.isbn13(),
	            item.cover(),
	            item.itemId()
	    );

	    // 3. DB에 저장
	    return bookRepository.save(book);
	}
	
	// YES24 검색 결과 중 이미 등록된 도서 ID 목록 반환
	@Transactional(readOnly = true)
	public Set<Long> getRegisteredYes24ItemIds(
	        List<Yes24BookResponse.BookItem> items) {

	    List<Long> itemIds = items.stream()
	            .map(Yes24BookResponse.BookItem::itemId)
	            .filter(java.util.Objects::nonNull)
	            .toList();

	    if (itemIds.isEmpty()) {
	        return Set.of();
	    }

	    return bookRepository.findAllByYes24ItemIdIn(itemIds)
	            .stream()
	            .map(Book::getYes24ItemId)
	            .collect(java.util.stream.Collectors.toSet());
	}
}
