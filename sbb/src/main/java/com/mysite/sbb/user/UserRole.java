package com.mysite.sbb.user;

import lombok.Getter;


@Getter
public enum UserRole {
    // 관리자와 일반 유저를 구분하기 위한 열거형 클래스
	ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}