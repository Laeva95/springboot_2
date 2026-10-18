package net.skhu.controller;


import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.skhu.entity.Department;
import net.skhu.model.DepartmentEdit;
import net.skhu.repository.DepartmentRepository;

@Controller
@Slf4j
@RequestMapping("department")
public class DepartmentController {

    @Autowired DepartmentRepository departmentRepository;
    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("list")
    public String list(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "department/list";
    }

    @GetMapping("edit")
    public String edit(Model model, @RequestParam("id") int id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        DepartmentEdit departmentEdit = modelMapper.map(department.get(), DepartmentEdit.class);
        model.addAttribute("departmentEdit", departmentEdit);
        return "department/edit";
    }
    @Transactional
    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, @Valid DepartmentEdit departmentEdit, BindingResult bindingResult) {
        try {
            log.debug(departmentEdit.toString());
            if (bindingResult.hasErrors())
                throw new Exception("저장할 수 없습니다");

            Department department = modelMapper.map(departmentEdit, Department.class);
            departmentRepository.save(department);
            return "redirect:list";
        }
        catch (Exception ex) {
            bindingResult.reject("", null, ex.getMessage());
            return "department/edit";
        }
    }
    @Transactional
    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, @Valid DepartmentEdit departmentEdit, BindingResult bindingResult)
    {
        try {
            departmentRepository.deleteById(departmentEdit.getId());
            return "redirect:list";
        }
        catch (Exception ex) {
            bindingResult.reject("", null, ex.getMessage());
            return "department/edit";
        }
    }

    @GetMapping("create")
    public String create(Model model) {
        DepartmentEdit departmentEdit = new DepartmentEdit();
        model.addAttribute("departmentEdit", departmentEdit);
        return "department/edit";
    }
    @Transactional
    @PostMapping("create")
    public String create(Model model, @Valid DepartmentEdit departmentEdit, BindingResult bindingResult) {
    	try {
    		log.debug(departmentEdit.toString());
    		if(bindingResult.hasErrors()) {
    			throw new Exception("저장 할 수 없습니다.");
    		}
    		
    		Department department = modelMapper.map(departmentEdit, Department.class);
    		departmentRepository.save(department);
    		return "redirect:list";
    	}
    	catch (Exception e) {
			bindingResult.reject("", null, e.getMessage());
			return "department/edit";
		}
    }
}