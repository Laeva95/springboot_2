
package com.myproject.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
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

    private LocalDate startDate;
    private LocalDate completedDate;

    // YES24 도서 정보
    private String publisher;
    private String isbn13;
    private String cover;

    // YES24에서 부여한 도서 식별자
    @Column(unique = true)
    private Long yes24ItemId;

    // JPA가 사용하는 기본 생성자
    protected Book() {
    }

    // 기존 생성자: 기존 코드와의 호환성 유지
    public Book(String title, String author, String status) {
        this(title, author, status, null, null);
    }

    // 날짜까지 설정하는 기존 생성자
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

    // YES24 도서 정보를 포함하는 생성자
    public Book(
            String title,
            String author,
            String status,
            String publisher,
            String isbn13,
            String cover,
            Long yes24ItemId) {

        this(title, author, status, null, null);

        this.publisher = publisher;
        this.isbn13 = isbn13;
        this.cover = cover;
        this.yes24ItemId = yes24ItemId;
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

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getCover() {
        return cover;
    }

    public Long getYes24ItemId() {
        return yes24ItemId;
    }

    // 기존 수정 메서드
    public void update(
            String title,
            String author,
            String status) {

        this.title = title;
        this.author = author;
        this.status = status;
    }

    // 날짜까지 수정하는 기존 메서드
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
