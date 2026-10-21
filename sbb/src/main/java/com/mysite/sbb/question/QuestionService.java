package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

import com.mysite.sbb.answer.Answer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
@Service		// 서비스 객체를 자동으로 생성
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
//	// 검색어를 매개변수로 받아 db에서 조회하는 메소드
//	private Specification<Question> search(String kw) {
//		// 인터페이스를 구현하는 익명 객체
//        return new Specification<>() {
//            private static final long serialVersionUID = 1L;
//            /*
//             	Root<Question>: 기준 테이블. q.get("subject")처럼 컬럼을 꺼냄
//             	CriteriaQuery<?>: 조회문 전체 쿼리. 중복 제거같은 설정을 여기에서 함
//             	CriteriaBuilder: 조건을 만드는 도구 객체
//             */
//            @Override
//            public Predicate toPredicate(Root<Question> q,
//            							CriteriaQuery<?> query, 
//            							CriteriaBuilder cb) {
//                query.distinct(true);  // 중복을 제거 
//                // Question의 author 속성으로 SiteUser와 조인
//                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
//                // Question의 answerList 속성으로 Answer와 조인
//                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
//                // Answer의 author 속성으로 SiteUser와 조인
//                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
//                
//                // cb.or(조건1, 조건2, 조건3, 조건4, 조건5)
//                // or 검색이므로 하나라도 조건을 충족한다면 결과에 포함
//                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
//                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
//                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
//                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
//                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
//            }
//        };
//    }
	
	// 모든 Question 값을 배열로 반환하는 메소드
	public Page<Question> getList(int page, String kw){
		// 생성일 기준으로 내림차순 정렬
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		
		// page: 조회할 페이지 번호, 10: 한 페이지에 보여줄 데이터
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

		// 클릭한 페이지 번호 + 한 페이지에 보여줄 데이터를 통해 데이터를 조회
		return this.questionRepository.findAllByKeyword(kw, pageable);
	}
	
	// 특정 id의 상세 페이지 데이터 반환
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		
		// 데이터가 존재한다면 반환
		if(question.isPresent()) {
			return question.get();
		} else { 
		// 데이터가 존재하지 않는다면 예외 처리
			throw new DataNotFoundException("question not found");
		}
	}
	
	// 새로운 질문 글 생성
	public void create(String subject, String content, SiteUser user) {
		// 추가할 새 질문 엔티티 생성
		Question q = new Question();
		
		// 엔티티에 데이터 추가
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		
		// DB에 행 추가
		questionRepository.save(q);
	}
	
	// 질문 글 수정
	public void modify(Question question, String subject, String content) {
		// 엔티티에 제목, 내용, 변경 날짜를 입력하고 DB에 저장
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }
	
	// 질문 글 삭제 요청
	public void delete(Question question) {
        this.questionRepository.delete(question);
    }
	
	// 질문 글 추천 요청
	public void vote(Question question, SiteUser siteUser) {
		// 질문 글 추천 Set에 로그인한 사용자를 추가
        question.getVoter().add(siteUser);
        
        // db에 저장
        this.questionRepository.save(question);
    }

}
