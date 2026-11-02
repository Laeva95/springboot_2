
package com.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import com.myproject.service.UserService;

import com.myproject.dto.SignupForm;

@Controller
public class AuthController {
	private final UserService userService;

	public AuthController(UserService userService) {
	    this.userService = userService;
	}

	// 회원가입 요청 처리
	@PostMapping("/signup")
	public String signup(
	        @Valid @ModelAttribute("signupForm") SignupForm form,
	        BindingResult bindingResult) {

	    // 입력값 검증 실패
	    if (bindingResult.hasErrors()) {
	        return "auth/signup";
	    }

	    // 회원가입 처리
	    try {
	        userService.register(form);
	    } catch (IllegalArgumentException e) {
	        bindingResult.rejectValue("username", "duplicate", e.getMessage());
	        return "auth/signup";
	    }

	    // 회원가입 성공 시 login 화면으로 이동
	    return "redirect:/login";
	}

	// 회원가입 페이지 요청
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }

    // 로그인 페이지 요청
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }
}
