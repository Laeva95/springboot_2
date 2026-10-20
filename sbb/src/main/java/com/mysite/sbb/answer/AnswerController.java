package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    // @PathVariable: 요청 주소로 전달 받은 변수값
    // @RequestParam: 요청 페이지에서 전달 받은 값(name="content"). request.getParameter("content")와 같음
    public String createAnswer(Model model, 
    							@PathVariable("id") Integer id, 
    							@Valid AnswerForm answerForm, 
    							BindingResult bindingResult, 
    							Principal principal) {
    	// 해당 질문글이 존재하는 id인지 확인
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        
        if(bindingResult.hasErrors()) {
        	model.addAttribute("question", question);
        	return "question_detail";
        }
        
        // 답변 저장 요청
        answerService.create(question, answerForm.getContent(), siteUser);
        
        return String.format("redirect:/question/detail/%s", id);
    }
    
    // 답변 수정 페이지 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
    	// id를 통해 현재 답변 엔티티 생성
        Answer answer = this.answerService.getAnswer(id);
        
        // 답변 작성자와 로그인한 사용자의 username이 동일한지 비교
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        // dto 객체에 현재 답변의 내용을 저장해서 view 페이지로 전달
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }
    
    // 답변 글 수정 요청
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, 
    							BindingResult bindingResult,
    							@PathVariable("id") Integer id, 
    							Principal principal) {
    	// 전달받은 데이터의 유효성 판단
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        
        // id를 통해 답변 엔티티 생성
        Answer answer = this.answerService.getAnswer(id);
        
        // 답변 작성자와 로그인한 사용자의 username이 동일한지 비교
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        // 답변 수정 요청
        this.answerService.modify(answer, answerForm.getContent());
        
        // 수정한 답변글이 존재하는 질문 글 상세 페이지 요청
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
    
    // 답변 글을 삭제 요청하는 메소드
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
    	// 전달 받은 id로 db에서 Answer 엔티티를 꺼냄
        Answer answer = this.answerService.getAnswer(id);
        
        // 답변 작성자와 로그인한 사용자의 username이 동일한지 비교
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        
        // 삭제 요청
        this.answerService.delete(answer);
        
        // 삭제된 답변글이 위치하던 질문 글 상세 보기 화면으로 리다이렉트
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
    
    // 답변 글을 추천 요청하는 메소드
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
    	// 전달 받은 id로 db에서 Answer 엔티티를 꺼냄
        Answer answer = this.answerService.getAnswer(id);
        
        // 로그인한 사용자의 정보가 담긴 엔티티를 꺼냄
        SiteUser siteUser = this.userService.getUser(principal.getName());
        
        // 두 엔티티의 정보를 통해 추천 요청
        this.answerService.vote(answer, siteUser);
        
        // 해당 답변글이 위치하던 질문 글 상세 보기 화면으로 리다이렉트
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
}
