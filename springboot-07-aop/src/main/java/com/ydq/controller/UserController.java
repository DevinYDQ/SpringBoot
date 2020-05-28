package com.ydq.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    //@GetMapping("/all")
    @PostMapping("/all")
    public String getUser(@RequestBody JSONObject jo) {
        return "test";
    }

}
