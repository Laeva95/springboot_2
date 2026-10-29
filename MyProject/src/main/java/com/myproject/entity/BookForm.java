package com.myproject.entity;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
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
    private LocalDate startDate;
    private LocalDate completedDate;

    // getter, setter
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
    
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }
    
    // 완료일이 시작일보다 빠를 수 없음
    @AssertTrue(message = "완독일은 독서 시작일보다 빠를 수 없습니다.")
    public boolean isDateRangeValid() {
        if (startDate == null || completedDate == null) {
            return true;
        }

        return !completedDate.isBefore(startDate);
    }
}