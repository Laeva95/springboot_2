package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	// 요청 URL: http://localhost:8070/sbb
	// GetMapping: HTTP Get 요청
    @GetMapping("/sbb")
    // ResponseBody: 메소드의 반환값을 브라우저로 전달
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신 것을 환영합니다.";
    }
}
