package com.ydq.springboot05mutidatasource.controller;

import com.ydq.springboot05mutidatasource.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("qm")
    public List<Map<String, Object>> queryStudentsFromMaster() throws InterruptedException {
        return this.studentService.getAllStudentsFromMaster();
    }

    @RequestMapping("qs")
    public List<Map<String, Object>> queryStudentsFromSlave() {
        return this.studentService.getAllStudentsFromSlave();
    }
}
