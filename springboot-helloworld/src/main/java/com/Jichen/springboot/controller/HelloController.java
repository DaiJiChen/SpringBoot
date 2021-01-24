package com.Jichen.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@ResponseBody // all methods in this calss response data directly to chrome
@RestController // RestController = Controller + ResponseBody
public class HelloController {

    @RequestMapping("/Hello")
    public String hello() {
        return "Hello World Quick";
    }
}

