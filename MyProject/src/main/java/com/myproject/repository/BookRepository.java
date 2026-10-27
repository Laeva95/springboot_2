package com.myproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	// status를 통해 검색
	List<Book> findByStatus(String status);
	// 문자열이 포함된 title 검색, 대소문자 무시
	List<Book> findByTitleContainingIgnoreCase(String keyword);
	// 문자열이 포함된 author 검색, 대소문자 무시
    List<Book> findByAuthorContainingIgnoreCase(String keyword);
}
