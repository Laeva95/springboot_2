package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	// Id: 기본키
	// GeneratedValue: 자동으로 숫자가 증가하면서 저장됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // length: 최대 길이 제한
    @Column(length = 200)
    private String subject;
    // columnDefinetion = "TEXT": 길이 제한이 거의 없는 문자열
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;
    // OneToMany: 일대다 관계를 말하며 질문 1개당 여러 답변이 달릴 수 있음
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) 
    private List<Answer> answerList; 
}
