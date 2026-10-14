package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@GetMapping("/hello")		// GET 요청 매핑
	@ResponseBody				// hello 메소드의 return 결과를 브라우저로 출력
	public String hello() {
		return "Hello Spring Boot";
	}
}
