
package com.myproject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

// 이 클래스를 엔티티로 설정
@Entity
// 테이믈 이름을 명시
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String role = "ROLE_USER";

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 기본 생성자
    protected User() {}

    // 아이디, 패스워드를 추가하는 생성자
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // JPA가 새로운 엔티티를 데이터베이스에 INSERT 하기 전에 자동으로 실행하는 메소드
    // 자동으로 가입 날짜 입력
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
