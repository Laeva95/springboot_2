
package com.myproject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.myproject.dto.Yes24BookResponse.BookItem;
import com.myproject.service.Yes24BookService;

@Controller
public class Yes24Controller {

    private final Yes24BookService yes24BookService;

    public Yes24Controller(Yes24BookService yes24BookService) {
        this.yes24BookService = yes24BookService;
    }

    // 도서 검색 화면
    @GetMapping("/books/search")
    public String search(
            @RequestParam(defaultValue = "") String query,
            Model model) {

        model.addAttribute("query", query);

        if (query.isBlank()) {
            model.addAttribute("books", List.of());
            return "books/api-search";
        }

        try {
            List<BookItem> books =
                    yes24BookService.searchBooks(query);

            model.addAttribute("books", books);

        } catch (RestClientException | IllegalStateException e) {
            model.addAttribute(
                    "errorMessage",
                    "도서 검색에 실패했습니다. API Key와 서버 로그를 확인해 주세요.");

            model.addAttribute("books", List.of());
        }

        return "books/api-search";
    }

    // 선택한 외부 도서의 상세 화면
    @GetMapping("/books/api/{itemId}")
    public String detail(
            @PathVariable Long itemId,
            Model model) {

        try {
            BookItem book =
                    yes24BookService.getBookDetail(itemId);

            model.addAttribute("book", book);

            return "books/api-detail";

        } catch (RestClientException | IllegalStateException e) {
            model.addAttribute(
                    "errorMessage",
                    "도서 상세 정보를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.");

            return "books/api-error";
        }
    }
}
