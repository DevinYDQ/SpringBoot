package com.ydq.springboot05mutidatasource.dao.masterDao;

import java.util.List;
import java.util.Map;

public interface MasterDatasourceMapper {
    List<Map<String, Object>> getAllStudents();
}
