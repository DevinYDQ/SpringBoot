package com.ydq.springboot05mutidatasource.service.impl;

import com.ydq.springboot05mutidatasource.dao.masterDao.MasterDatasourceMapper;
import com.ydq.springboot05mutidatasource.service.StudentService;
import com.ydq.springboot05mutidatasource.dao.slaveDao.SlaveDatasouceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private MasterDatasourceMapper masterDatasourceMapper;

    @Autowired
    private SlaveDatasouceMapper slaveDatasouceMapper;

    @Override
    public List<Map<String, Object>> getAllStudentsFromMaster() {
        return masterDatasourceMapper.getAllStudents();
    }

    @Override
    public List<Map<String, Object>> getAllStudentsFromSlave() {
        return slaveDatasouceMapper.getAllStudents();
    }
}
