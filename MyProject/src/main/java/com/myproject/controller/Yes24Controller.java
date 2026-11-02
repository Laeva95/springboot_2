
package com.myproject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myproject.dto.Yes24BookResponse;
import com.myproject.dto.Yes24BookResponse.BookItem;
import com.myproject.service.BookService;
import com.myproject.service.Yes24BookService;

@Controller
public class Yes24Controller {

    private final Yes24BookService yes24BookService;
    private final BookService bookService;

    public Yes24Controller(
            Yes24BookService yes24BookService,
            BookService bookService) {
        this.yes24BookService = yes24BookService;
        this.bookService = bookService;
    }

 // 도서 검색 화면
    @GetMapping("/books/search")
    public String search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        // 페이지 번호가 1보다 작으면 1로 보정
        page = Math.max(1, page);

        model.addAttribute("query", query);
        model.addAttribute("currentPage", page);
        model.addAttribute("registeredItemIds", java.util.Set.of());
        model.addAttribute("totalPages", 0);

        if (query.isBlank()) {
            model.addAttribute("books", List.of());
            return "books/api-search";
        }

        try {
            // 페이지 번호를 지정해 YES24 검색
            Yes24BookResponse.Data searchData =
                    yes24BookService.searchBooks(query, page);

            List<BookItem> books = searchData.items() == null
                    ? List.of()
                    : searchData.items();

            int pageSize = searchData.pageSize() == null
                    || searchData.pageSize() <= 0
                    ? 10
                    : searchData.pageSize();

            int totalCount = searchData.totalCount() == null
                    ? books.size()
                    : searchData.totalCount();

            int currentPage = searchData.currentPage() == null
                    ? page
                    : searchData.currentPage();

            int totalPages = (int) Math.ceil(
                    (double) totalCount / pageSize);
            
            // 10페이지 단위로 페이지 그룹 계산
            int startPage = ((currentPage - 1) / 10) * 10 + 1;

            int endPage = Math.min(startPage + 9, totalPages);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("books", books);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalCount", totalCount);

            // 검색 결과 중 이미 내 서재에 등록된 도서 ID
            model.addAttribute(
                    "registeredItemIds",
                    bookService.getRegisteredYes24ItemIds(books));

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
    
 // YES24 도서를 내 서재에 추가
    @PostMapping("/books/api/{itemId}/add")
    public String addToLibrary(
            @PathVariable Long itemId,
            RedirectAttributes redirectAttributes) {

        try {
            // YES24에서 해당 도서의 상세 정보 조회
            BookItem book =
                    yes24BookService.getBookDetail(itemId);

            // 조회한 도서를 내 서재 DB에 저장
            bookService.saveBookFromYes24(book);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "도서가 내 서재에 추가되었습니다.");

            // 내 책 목록으로 이동
            return "redirect:/books";

        } catch (IllegalArgumentException e) {
            // 이미 등록한 도서 등의 입력 관련 오류
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());

            // 도서 상세 화면으로 이동
            return "redirect:/books/api/" + itemId;

        } catch (RestClientException | IllegalStateException e) {
            // YES24 API 조회 과정에서 발생한 오류
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "도서 정보를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.");

            // 도서 상세 화면으로 이동
            return "redirect:/books/api/" + itemId;
        }
    }
}
