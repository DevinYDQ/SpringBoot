package com.ydq.controller;

import com.ydq.domain.User;
import com.ydq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User getUserById(@PathVariable(value = "id") String userId) {
//        System.out.println("1==========");
//        userService.getUserById(userId);
//        System.out.println("2====================");
        return userService.getUserById(userId);
    }

}
