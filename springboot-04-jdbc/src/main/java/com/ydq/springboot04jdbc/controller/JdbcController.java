package com.ydq.springboot04jdbc.controller;

import com.ydq.springboot04jdbc.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jdbc")
public class JdbcController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/user")
    public List<User> queryUser() {
        String sql = "select * from user";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;

            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setSex(rs.getString("sex"));
                user.setAge(rs.getString("age"));
                return user;
            }
        });

//        List < Map < String, Object >> maps = jdbcTemplate.queryForList(sql);
//        System.out.println(maps);
        return userList;
    }
}
