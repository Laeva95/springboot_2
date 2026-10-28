package com.myproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	// status를 통해 검색
	List<Book> findByStatus(String status);
	
	// 제목, 검색어, 페이지를 통해 검색
	@Query(value = """
            SELECT b FROM Book b
            WHERE (:status = 'ALL' OR b.status = :status)
              AND (
                  :keyword = ''
                  OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
            """,
        countQuery = """
            SELECT COUNT(b) FROM Book b
            WHERE (:status = 'ALL' OR b.status = :status)
              AND (
                  :keyword = ''
                  OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
            """)
	Page<Book> searchBooks(
            @Param("status") String status,
            @Param("keyword") String keyword,
            Pageable pageable);
}
