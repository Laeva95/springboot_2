package net.skhu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.skhu.dto.Student;

@Controller
@RequestMapping("/student")
@Slf4j
public class StudentController {
	@GetMapping("/edit")
	public String edit(Model model) {
		Student student = new Student();
		student.setName("정운학");
		student.setStudentNo("2014xxxx");
		student.setDepartmentId(3);
		student.setGender("남자");
		student.setYear(2014);
		student.setAbsense(false);
		log.debug(student.toString());
		model.addAttribute("student", student);
		return "/student/edit";
	}
	
	@PostMapping("/edit")
	public String edit(Model model, Student student, BindingResult bindingResult) {
		try {
			log.debug(student.toString());
			if(bindingResult.hasErrors()) {
				throw new Exception("저장 할 수 없습니다.");
			}
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
