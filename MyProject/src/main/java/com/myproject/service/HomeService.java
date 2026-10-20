package com.myproject.service;

import org.springframework.stereotype.Service;

// 스프링 서비스
@Service
public class HomeService {
	
	public String getHomeMessage() {
		return "Spring Boot와 함께하는 나의 독서 기록";
	}
}
