package com.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myproject.entity.Book;
import com.myproject.service.BookService;

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
    public String bookForm() {
        return "books/form";
    }

    // 3. 책 등록 처리
    @PostMapping("/books")
    public String createBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        bookService.saveBook(title, author, status);

        redirectAttributes.addFlashAttribute(
                "message", "책이 등록되었습니다.");

        return "redirect:/books";
    }
    
    // 수정 폼 표시
    @GetMapping("/books/{id}/edit")
    public String editBookForm(
            @PathVariable Long id, Model model) {

        Book book = bookService.findBook(id);
        model.addAttribute("book", book);

        return "books/edit";
    }

    // 수정 내용 저장
    @PostMapping("/books/{id}/edit")
    public String updateBook(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        bookService.updateBook(id, title, author, status);

        redirectAttributes.addFlashAttribute(
                "message", "책 정보가 수정되었습니다.");

        return "redirect:/books";
    }
}
