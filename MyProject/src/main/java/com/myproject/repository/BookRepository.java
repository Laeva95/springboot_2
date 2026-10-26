package com.myproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByStatus(String status);
}
