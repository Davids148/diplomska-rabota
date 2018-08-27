package com.david.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/david")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "home";
    }

}
