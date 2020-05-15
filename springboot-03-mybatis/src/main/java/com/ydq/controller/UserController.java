package com.ydq.controller;

import com.ydq.domain.User;
import com.ydq.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
//    @Resource
    @Autowired
    private UserMapper userMapper;

    @GetMapping("{id}")
    User getUser(@PathVariable(value = "id") String userId) {
        return userMapper.getUserById(userId);
    }
}
