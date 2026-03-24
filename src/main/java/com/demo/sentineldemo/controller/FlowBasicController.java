package com.demo.sentineldemo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
    // 资源1：下单接口（会被关联限流）
    @SentinelResource(value = "order", blockHandler = "orderBlockHandler")
    @GetMapping("/order")
    public String order() {
        return "下单成功 ✅";
    }

    // 下单接口被限流时的降级处理
    public String orderBlockHandler(BlockException e) {
        return "下单失败 ❌：支付接口压力过大，暂时限制下单";
    }

    // 资源2：支付接口（关联资源）
    @SentinelResource(value = "pay")
    @GetMapping("/pay")
    public String pay() {
        return "支付成功 ✅";
    }


}
