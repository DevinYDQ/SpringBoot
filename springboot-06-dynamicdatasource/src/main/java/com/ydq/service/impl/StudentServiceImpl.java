package com.ydq.service.impl;

import com.ydq.config.db.AllDB;
import com.ydq.config.db.DbRoute;
import com.ydq.dao.StudentMapper;
import com.ydq.domain.Student;
import com.ydq.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    @DbRoute(AllDB.Master.class)
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }
}
