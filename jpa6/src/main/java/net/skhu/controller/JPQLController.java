package net.skhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import net.skhu.entity.Student;

@Controller
public class JPQLController {

    @Autowired JpaContext jpaContext;

    @GetMapping("jpql")
    public String index() {
        return "jpql/index";
    }

    @GetMapping(value="jpql", params="q")
    public @ResponseBody Object jpql(@RequestParam("q") String q) {
        EntityManager manager = jpaContext.getEntityManagerByManagedType(Student.class);
        Query query = manager.createQuery(q);
        Object r = query.getResultList();
        return r;
    }
}