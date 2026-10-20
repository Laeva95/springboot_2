package com.mysite.sbb.answer;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    // 질문에 대한 답변 생성 요청
    public void create(Question question, String content, SiteUser author) {
    	// 새 답변 엔티티 생성
        Answer answer = new Answer();
        
        // 답변 엔티티 내용, 작성 시간, 부모 질문글 저장
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        // 리포지터리에 저장 요청
        this.answerRepository.save(answer);
    }
    
    // id를 통해 db에서 답변 글을 조회하고 반환하는 메소드
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    // 전달받은 답변 엔티티의 내용을 수정해서 db에 저장하는 메소드
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    
    // 삭제 요청 처리
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
    
    // 답변 글 추천 요청 처리
    public void vote(Answer answer, SiteUser siteUser) {
    	// 추천 목록 Set에 로그인한 사용자 추가
        answer.getVoter().add(siteUser);
        
        // db에 저장
        this.answerRepository.save(answer);
    }
}