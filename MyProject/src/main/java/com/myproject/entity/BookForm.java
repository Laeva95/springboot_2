package com.myproject.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BookForm {
	// @NotBlank: null이거나 빈 문자열, 공백 문자열인지 확인
	// message: 유효성 검사에 실패했을 경우 출력하는 메세지
    @NotBlank(message = "책 제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "저자명을 입력해 주세요.")
    private String author;

    // regexp: 문자열이 지정한 정규식에 맞는지 확인
    @Pattern(
        regexp = "PLANNED|READING|COMPLETED",
        message = "올바른 독서 상태를 선택해 주세요."
    )
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}