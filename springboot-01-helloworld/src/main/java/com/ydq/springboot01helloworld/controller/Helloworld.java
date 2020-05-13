package com.ydq.springboot01helloworld.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Helloworld {
    @RequestMapping("/hello")
    public String hello() {
        return "hello world, springboot!";
    }
}
