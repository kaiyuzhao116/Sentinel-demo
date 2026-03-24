package com.demo.sentineldemo.controller;

import com.demo.sentineldemo.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/flow/chain")
public class FlowChainController {
    @Resource
    private TestService testService;

    // 链路1：管理员端调用，不限流
    @GetMapping("/admin/query")
    public String adminQuery() {
        return "管理员端：" + testService.commonQuery();
    }

    // 链路2：用户端调用，需要限流
    @GetMapping("/user/query")
    public String userQuery() {
        return "用户端：" + testService.commonQuery();
    }
}
