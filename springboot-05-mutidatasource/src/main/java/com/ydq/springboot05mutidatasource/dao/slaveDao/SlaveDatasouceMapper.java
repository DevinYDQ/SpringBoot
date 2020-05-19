package com.ydq.springboot05mutidatasource.dao.slaveDao;

import java.util.List;
import java.util.Map;

public interface SlaveDatasouceMapper {
    List<Map<String, Object>> getAllStudents();
}
