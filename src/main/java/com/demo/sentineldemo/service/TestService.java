package com.demo.sentineldemo.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * 同一个 Service 方法，被两个不同的 Controller 接口调用，
 * 只限制其中一个链路的请求。
 */
@Service
public class TestService {
    // 定义Sentinel资源名，链路限流基于这个资源
    @SentinelResource("commonQuery")
    public String commonQuery() {
        return "公共查询方法执行成功";
    }
}
