package com.community.communitybackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //告诉 Spring Boot 这是一个控制器类。
public class TestController {

    @GetMapping("/hello")
    // 定义了一个处理 GET 请求的接口，路径是 /hello。结合我们配置的 context-path，完整的访问地址就是 /api/hello
    public String sayHello() {
        return "Hello from Backend!";
    }
}