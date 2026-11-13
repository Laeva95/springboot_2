package com.myproject.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// 스프링부트 Controller 클래스에서 발생하는 예외를 공통으로 처리
@ControllerAdvice
public class GlobalExceptionHandler {

	// 어떤 예외를 처리할지 지정하는 어노테이션
    @ExceptionHandler(NoSuchElementException.class)
    // 해당 예외 코드의 HTTP 응답 상태 코드를 설정하는 어노테이션
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(
            NoSuchElementException e,
            Model model) {

        model.addAttribute("message", e.getMessage());

        return "error/404";
    }

    // 잘못된 요청 값 처리 (예: /books?status=xxx)
    // 컨트롤러/서비스에서 직접 던진 IllegalArgumentException 중
    // 각 컨트롤러가 try-catch로 처리하지 않은 것만 이곳으로 옴
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(
            IllegalArgumentException e,
            Model model) {

        model.addAttribute("message", e.getMessage());

        return "error/400";
    }

    // 요청 파라미터 타입 변환 실패 처리 (예: /books?page=abc, /books/abc/edit)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeMismatch(
            MethodArgumentTypeMismatchException e,
            Model model) {

        model.addAttribute("message",
                "요청 값이 올바르지 않습니다: " + e.getName());

        return "error/400";
    }
}
