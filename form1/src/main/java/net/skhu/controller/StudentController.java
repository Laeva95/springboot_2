package net.skhu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.dto.Student;

@Controller
@RequestMapping("/student")
public class StudentController {
	@GetMapping("/edit")
	public String edit(Model model) {
		Student student = new Student();
		student.setName("홍길동");
		student.setStudentNo("2000");
		model.addAttribute("student", student);
		return "/student/edit";
	}
	
	@PostMapping("/edit")
	public String edit(Model model, Student student) {
		try {
			if(StringUtils.hasText(student.getStudentNo()) == false)
				throw new Exception("학번을 입력하세요");
			if(StringUtils.hasText(student.getName()) == false)
				throw new Exception("이름을 입력하세요");
			
			System.out.println(student.getStudentNo() + " " + student.getName());
			return "redirect:/student/list";
		} catch (Exception e) {
			model.addAttribute("errorMsg", e.getMessage());
			return "/student/edit";
		}
	}
	
	@GetMapping("/list")
	public String list() {
		return "/student/list";
	}
}
