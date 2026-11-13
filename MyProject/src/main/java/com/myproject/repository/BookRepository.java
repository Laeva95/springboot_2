package com.myproject.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	// status를 통해 검색
	List<Book> findByStatus(String status);
	
	// 특정 사용자가 해당 YES24 도서를 이미 등록했는지 확인
	boolean existsByUserIdAndYes24ItemId(Long userId, Long yes24ItemId);
	
	// 특정 사용자의 특정 도서 조회
	Optional<Book> findByIdAndUserId(Long id, Long userId);

	// 특정 사용자가 등록한 도서 전체 조회
	List<Book> findByUserId(Long userId);

	// 특정 사용자가 등록한 도서 수 조회
	long countByUserId(Long userId);
	
	// 특정 사용자가 등록한 특정 상태의 도서 전체 조회
	List<Book> findByUserIdAndStatus(Long userId, String status);
	
	// 특정 사용자가 등록한 도서 중 주어진 YES24 도서 ID에 해당하는 도서 조회
	List<Book> findAllByUserIdAndYes24ItemIdIn(Long userId, Collection<Long> yes24ItemIds);
	
	// 사용자별 도서 검색 및 페이징
	@Query(value = """
	        SELECT b FROM Book b
	        WHERE b.user.id = :userId
	          AND (:status = 'ALL' OR b.status = :status)
	          AND (
	              :keyword = ''
	              OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
	              OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
	          )
	        """,
	    countQuery = """
	        SELECT COUNT(b) FROM Book b
	        WHERE b.user.id = :userId
	          AND (:status = 'ALL' OR b.status = :status)
	          AND (
	              :keyword = ''
	              OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
	              OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
	          )
	        """)
	Page<Book> searchBooks(
	        @Param("userId") Long userId,
	        @Param("status") String status,
	        @Param("keyword") String keyword,
	        Pageable pageable);
	
	
}
