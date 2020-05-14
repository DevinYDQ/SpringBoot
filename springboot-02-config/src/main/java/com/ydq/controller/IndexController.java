package com.ydq.controller;

import com.ydq.bean.BlogProperties;
import com.ydq.bean.ConfigBean;
import com.ydq.bean.TestConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private ConfigBean configBean;

    @Autowired
    private TestConfigBean testConfigBean;

    @RequestMapping("/config")
    String index() {
       // return blogProperties.getName() + ", " + blogProperties.getTitle();
       // return configBean.getName() + ", " + configBean.getTitle() + ", " + configBean.getWholeTitle();
        return testConfigBean.getName() + ", " + testConfigBean.getAge();
    }
}
