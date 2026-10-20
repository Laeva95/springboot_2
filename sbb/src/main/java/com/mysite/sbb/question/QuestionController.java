package com.mysite.sbb.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
	
	private final AuthenticationManager authenticationManager;
	private final QuestionService questionService;
	private final UserService userService;
	
	// 처리할 URL GET 요청 매핑
	@GetMapping("/list")    
	// 결과 값을 브라우저에 출력
	// @RequestParam(defaultValue=""): 값을 전달 받지 않았을 때 기본 값	
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {   // Model: 브라우저로 응답할 데이터를 보관하는 메모리
		Page<Question> paging = this.questionService.getList(page);
		
		model.addAttribute("paging", paging);
		
		return "question_list";			
	}
	
	// 질문글 상세 조회
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		
        model.addAttribute("question", question);
        
		return "question_detail";
	}
	
	// 질문글 생성 폼 요청
	// @PreAuthorize("isAuthenticated()"): 로그인 하지 않은 상태라면 SecurityConfig 클래스에서 설정한 로그인 페이지로 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	// 질문글 생성 요청
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		// 유효성 검사가 실패할 경우 제목과 내용을 작성하는 화면으로 돌아감
		if(bindingResult.hasErrors()) {
			return "question_form";
		}

		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		// 유효성 검사에 성공할 경우 데이터 입력 후 전체 목록 화면으로 리다이렉트
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}
	
	// 질문 글 수정 페이지 요청
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        
        // 질문 작성자와 로그인 유저 username이 동일한지 비교
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        // DB에서 조회한 정보를 dto 객체에 저장
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }
	
	// 질문 글 수정 요청
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
		// 질문 정보 유효성 검사
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        
        // 유효성 검사 통과 시 수정할 질문 엔티티를 가져옴
        Question question = this.questionService.getQuestion(id);
        
        // 질문 작성자와 로그인한 사용자의 username이 동일한지 비교
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        // 가져온 질문 엔티티, 전달받은 수정된 글 제목, 내용을 전달해서 수정 요청
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        
        // 질문 글 수정에 성공하면 해당 글 상세보기 화면으로 리다이렉트
        return String.format("redirect:/question/detail/%s", id);
    }
	
	// 질문 글 삭제 요청
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		// 전달 받은 id 값을 통해 질문 엔티티 생성
        Question question = this.questionService.getQuestion(id);
        
        // 질문 글 작성자와 로그인 한 사용자의 username이 동일한지 비교
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        
        // 질문 글 삭제
        this.questionService.delete(question);
        
        // 메인 화면으로 이동
        return "redirect:/";
    }
	
	// 질문 글 추천 요청
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		// 전달 받은 id 값을 통해 질문 엔티티 생성
        Question question = this.questionService.getQuestion(id);
        
        // 로그인한 사용자 정보를 가져옴
        SiteUser siteUser = this.userService.getUser(principal.getName());
        
        // 로그인한 사용자 정보를 통해 추천 요청
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
