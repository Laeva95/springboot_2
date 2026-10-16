package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    // @PathVariable: 요청 주소로 전달 받은 변수값
    // @RequestParam: 요청 페이지에서 전달 받은 값(name="content"). request.getParameter("content")와 같음
    public String createAnswer(Model model, @PathVariable("id") Integer id
    		, @Valid AnswerForm answerForm, BindingResult bindingResult) {
    	// 해당 질문글이 존재하는 id인지 확인
        Question question = this.questionService.getQuestion(id);
        
        if(bindingResult.hasErrors()) {
        	model.addAttribute("question", question);
        	return "question_detail";
        }
        
        // 답변 저장 요청
        answerService.create(question, answerForm.getContent());
        
        return String.format("redirect:/question/detail/%s", id);
    }
}
