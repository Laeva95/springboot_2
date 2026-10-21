package com.myproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String author;
	private String status;
	
	// 생성자
	protected Book() {
		
	}
	public Book(String title, String author, String status) {
		this.title = title;
		this.author = author;
		this.status = status;
	}
	
	// Getter
	public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }
    
    // Book 엔티티의 내용을 수정할 수 있는 메소드
    public void update(String title, String author, String status) {
        this.title = title;
        this.author = author;
        this.status = status;
    }
}
