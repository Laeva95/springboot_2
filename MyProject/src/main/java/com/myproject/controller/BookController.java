package com.myproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.entity.Book;
import com.myproject.service.BookService;

// 스프링 컨트롤러
// 응답 본문에 데이터를 직접 반환하는 컨트롤러
@RestController
public class BookController {

	private final BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping("/test/save-book")
	public String saveTestBook() {
		Book book = bookService.saveBook(
				"클린 코드",
				"로버트 C 마틴", 
				"READING");
		return "책 저장 성공! ID: " + book.getId();
	}
}
