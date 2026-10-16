package net.skhu.controller;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.skhu.entity.Student;
import net.skhu.model.StudentEdit;
import net.skhu.repository.DepartmentRepository;
import net.skhu.repository.StudentRepository;

@Controller
@Slf4j
@RequestMapping("student")
public class StudentController {

    @Autowired StudentRepository studentRepository;
    @Autowired DepartmentRepository departmentRepository;
    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("list")
    public String list(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "student/list";
    }

    @GetMapping("edit")
    public String edit(Model model, @RequestParam("id") int id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        StudentEdit studentEdit = modelMapper.map(student.get(), StudentEdit.class);
        model.addAttribute("studentEdit", studentEdit);
        model.addAttribute("departments", departmentRepository.findAll());
        return "student/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, @Valid StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            log.debug(studentEdit.toString());
            if (bindingResult.hasErrors())
                throw new Exception("저장할 수 없습니다");

            Student student = modelMapper.map(studentEdit, Student.class);
            
            studentRepository.save(student);
            return "redirect:list";
        }
        catch (Exception ex) {
            bindingResult.reject("", null, ex.getMessage());
            model.addAttribute("departments", departmentRepository.findAll());
            return "student/edit";
        }
    }
    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, @Valid StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentRepository.deleteById(studentEdit.getId());
            return "redirect:list";
        }
        catch (Exception ex) {
            bindingResult.reject("", null, ex.getMessage());
            model.addAttribute("departments", departmentRepository.findAll());
            return "student/edit";
        }
    }

    @GetMapping("create")
    public String create(Model model) {
        StudentEdit studentEdit = new StudentEdit();
        model.addAttribute("studentEdit", studentEdit);
        model.addAttribute("departments", departmentRepository.findAll());
        return "student/edit";
    }

    @PostMapping("create")
    public String create(Model model, @Valid StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            log.debug(studentEdit.toString());
            if (bindingResult.hasErrors())
                throw new Exception("저장할 수 없습니다");

            Student student = modelMapper.map(studentEdit, Student.class);
                 
            studentRepository.save(student);
            return "redirect:list";
        }
        catch (Exception ex) {
            bindingResult.reject("", null, ex.getMessage());
            model.addAttribute("departments", departmentRepository.findAll());
            return "student/edit";
        }
    }
}
