package com.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myproject.service.HomeService;

// 스프링 컨트롤러
@Controller
public class HomeController {
	
	private final HomeService homeService;
	
	// 생성자를 통해 객체 주입
	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		
		String message = homeService.getHomeMessage();
		
		model.addAttribute("message", message);
		
		return "home";
	}
}
