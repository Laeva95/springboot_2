package com.myproject.entity;

import java.time.LocalDate;

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
	
	// 새로 추가할 날짜 필드
    private LocalDate startDate;
    private LocalDate completedDate;
	
	// 생성자
	protected Book() {
		
	}
	public Book(
			String title, 
			String author, 
			String status) {
		
		this(title, author, status, null, null);
	}
	
	// 날짜까지 설정할 수 있는 생성자
    public Book(
            String title,
            String author,
            String status,
            LocalDate startDate,
            LocalDate completedDate) {

        this.title = title;
        this.author = author;
        this.status = status;
        this.startDate = startDate;
        this.completedDate = completedDate;
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
    
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }
    
    // Book 엔티티의 내용을 수정할 수 있는 메소드
    public void update(
    		String title, 
    		String author, 
    		String status) {
    	
        this.title = title;
        this.author = author;
        this.status = status;
    }
    
    // 날짜까지 수정할 수 있는 메소드
    public void update(
            String title,
            String author,
            String status,
            LocalDate startDate,
            LocalDate completedDate) {

        this.title = title;
        this.author = author;
        this.status = status;
        this.startDate = startDate;
        this.completedDate = completedDate;
    }
}
