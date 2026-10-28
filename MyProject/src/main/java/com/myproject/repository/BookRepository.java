package com.myproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	// status를 통해 검색
	List<Book> findByStatus(String status);
	@Query("""
	        SELECT b FROM Book b
	        WHERE (:status = 'ALL' OR b.status = :status)
	          AND (
	              :keyword = ''
	              OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
	              OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
	          )
	        """)
	List<Book> searchBooks(
            @Param("status") String status,
            @Param("keyword") String keyword);
}
