package com.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myproject.entity.Book;
import com.myproject.entity.BookForm;
import com.myproject.service.BookService;

import jakarta.validation.Valid;

// 스프링 컨트롤러
@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 1. 책 목록 페이지
    @GetMapping("/books")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    // 2. 책 등록 폼 페이지
    @GetMapping("/books/new")
    public String bookForm(Model model) {
    	model.addAttribute("bookForm", new BookForm());
        return "books/form";
    }

    // 3. 책 등록 처리
    @PostMapping("/books")
    public String createBook(
    		// @Valid: 객체에 선언한 검증 규칙을 실행하여 유효성 검사
    		// @ModelAttribute: 요청으로 전달된 데이터를 매개변수로 받음
    		// BindingResult: 검증 결과를 담는 객체
    		@Valid @ModelAttribute("bookForm") BookForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

    	// 입력값 검증에 실패했다면 등록 화면으로 돌아간다.
        if (bindingResult.hasErrors()) {
            return "books/form";
        }

        // 검증을 통과한 경우에만 DB에 저장한다.
        bookService.saveBook(
            form.getTitle(),
            form.getAuthor(),
            form.getStatus()
        );

        redirectAttributes.addFlashAttribute(
                "message", "책이 등록되었습니다.");

        return "redirect:/books";
    }
    
    // 수정 폼 표시
    @GetMapping("/books/{id}/edit")
    public String editBookForm(
            @PathVariable Long id, Model model) {

    	// id 값을 통해 db에서 책 엔티티를 가져옴
        Book book = bookService.findBook(id);
        
        // BookForm 객체를 생성하여 엔티티의 정보 입력
        BookForm form = new BookForm();
        form.setTitle(book.getTitle());
        form.setAuthor(book.getAuthor());
        form.setStatus(book.getStatus());
        
        // 모델 객체를 통해 BookForm 객체 전달
        model.addAttribute("bookForm", form);
        model.addAttribute("bookId", id);

        return "books/edit";
    }

    // 수정 내용 저장
    @PostMapping("/books/{id}/edit")
    public String updateBook(
            @PathVariable Long id,
            @Valid @ModelAttribute("bookForm") BookForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

    	// 검증에 실패하면 수정 화면을 다시 보여 준다.
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookId", id);
            return "books/edit";
        }
        
        // 검증을 통과한 경우에만 책 정보를 수정한다.
        bookService.updateBook(id, form.getTitle(), form.getAuthor(), form.getStatus());

        redirectAttributes.addFlashAttribute("message", "책 정보가 수정되었습니다.");

        return "redirect:/books";
    }
    
    // 책 데이터 삭제 요청
    @PostMapping("/books/{id}/delete")
    public String deleteBook(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        bookService.deleteBook(id);

        redirectAttributes.addFlashAttribute("message", "책이 삭제되었습니다.");

        return "redirect:/books";
    }
}
