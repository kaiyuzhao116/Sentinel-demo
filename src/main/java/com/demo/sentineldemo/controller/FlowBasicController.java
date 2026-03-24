package com.demo.sentineldemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow/basic")
public class FlowBasicController {
    @GetMapping("/hello")
    public String hello() {


        return "Hello Sentinel! 基础限流测试成功";
    }


}
