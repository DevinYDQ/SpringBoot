package com.ydq.springboot05mutidatasource.service;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Map<String, Object>> getAllStudentsFromMaster();
    List<Map<String, Object>> getAllStudentsFromSlave();
}
