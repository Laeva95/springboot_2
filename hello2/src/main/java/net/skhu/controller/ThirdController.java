package net.skhu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import net.skhu.dto.Student;


@Controller
@RequestMapping("third")
public class ThirdController {
	@GetMapping("test1")
	public String test1(Model model) {
		Student student = new Student(1, "123123", "홍길동", "hong@test.com");
		
		model.addAttribute("student", student);
		
		return "third/test1";
	}
	
}
