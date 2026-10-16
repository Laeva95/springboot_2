package net.skhu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.skhu.dto.Student;
import net.skhu.model.StudentEdit;

@Controller
@RequestMapping("/student")
@Slf4j
public class StudentController {
	@GetMapping("/edit")
	public String edit(Model model) {
		StudentEdit student = new StudentEdit();
		student.setName("정운학");
		log.debug(student.toString());
		model.addAttribute("studentEdit", student);
		return "/student/edit";
	}
	
	@PostMapping("/edit")
	public String edit(Model model,@Valid StudentEdit student, BindingResult bindingResult) {
		try {
			log.debug(student.toString());
			if(bindingResult.hasErrors()) {
				throw new Exception("저장 할 수 없습니다.");
			}
			System.out.println(student.getStudentNo() + " " + student.getName());
			return "redirect:/student/list";
		} catch (Exception e) {
			bindingResult.reject("", null, e.getMessage());
			return "/student/edit";
		}
	}
	
	@GetMapping("/list")
	public String list() {
		return "/student/list";
	}
}
