package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	
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
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	// 질문글 생성 요청
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		// 유효성 검사가 실패할 경우 제목과 내용을 작성하는 화면으로 돌아감
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		// 유효성 검사에 성공할 경우 데이터 입력 후 전체 목록 화면으로 리다이렉트
		this.questionService.create(questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list";
	}
}
