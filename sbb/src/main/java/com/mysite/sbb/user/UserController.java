package com.mysite.sbb.user;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
    	// 가입을 위해 입력한 정보를 제대로 입력하지 않아 유효성 검사에 실패했을 경우
        if (bindingResult.hasErrors()) {
        	// 회원가입 요청 폼 화면으로 이동
            return "signup_form";
        }

        // 입력한 비밀번호와 비밀번호 확인 값이 다를 경우
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
        	// 에러 메세지 저장
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            
            // 회원가입 요청 폼 화면으로 이동
            return "signup_form";
        }

        try {
        	// DB에 데이터 추가
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
        	// 이미 등록된 사용자일 경우 데이터를 추가 하지 않음
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }
    
    // 로그인 할 수 있는 화면 요청
    @GetMapping("/login")
    public String login() {
    	return "login_form";
    }
}
