package net.skhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.skhu.entity.Student;
import net.skhu.repository.StudentRepository;

@Controller
public class StudentController {

    @Autowired StudentRepository studentRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("student/test1")
    public String test1(Model model, @RequestParam("studentNo") String studentNo) {
        Student student = studentRepository.findByStudentNo(studentNo);
        model.addAttribute("students", student);
        return "student/list";
    }

    @GetMapping("student/test2")
    public String test2(Model model, @RequestParam("name") String name) {
        model.addAttribute("students",
                studentRepository.findByName(name));
        return "student/list";
    }

    @GetMapping("student/test3")
    public String test3(Model model, @RequestParam("nm") String name) {
        model.addAttribute("students",
                studentRepository.findByNameStartsWith(name));
        return "student/list";
    }

    @GetMapping("student/test4")
    public String test4(Model model, @RequestParam("dn") String departmentName) {
        model.addAttribute("students",
                studentRepository.findByDepartmentName(departmentName));
        return "student/list";
    }

    @GetMapping("student/test5")
    public String test5(Model model, @RequestParam("dn") String departmentName) {
        model.addAttribute("students",
                studentRepository.findByDepartmentNameStartsWith(departmentName));
        return "student/list";
    }
    @GetMapping("student/test6")
    public String test6(Model model) {
        model.addAttribute("students",
                studentRepository.findByOrderByName());
        return "student/list";
    }

    @GetMapping("student/test7")
    public String test7(Model model) {
        model.addAttribute("students",
                studentRepository.findByOrderByNameDesc());
        return "student/list";
    }

    @GetMapping("student/test8")
    public String test8(Model model, @RequestParam("di") int departmentId) {
        model.addAttribute("students",
                studentRepository.findByDepartmentIdOrderByNameDesc(departmentId));
        return "student/list";
    }
    
}