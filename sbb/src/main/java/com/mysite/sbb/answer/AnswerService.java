package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    // 질문에 대한 답변 생성 요청
    public void create(Question question, String content) {
    	// 새 답변 엔티티 생성
        Answer answer = new Answer();
        
        // 답변 엔티티 내용 입력
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        
        // 리포지터리에 저장 요청
        this.answerRepository.save(answer);
    }
}