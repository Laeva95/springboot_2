package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private LocalDateTime modifyDate;
    
    // OneToMany: 일대다 관계를 말하며 질문 1개당 여러 답변이 달릴 수 있음
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) 
    private List<Answer> answerList;
    
    // 질문을 작성한 회원 정보를 담는 변수
    @ManyToOne
    private SiteUser author;
    
    // 질문 글을 추천한 회원들 Set
    // Set 특징: 중복 저장 불가능, 순서 없음
    @ManyToMany
    Set<SiteUser> voter;
}
