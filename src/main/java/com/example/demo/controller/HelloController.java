

package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot! 환영해요! 💖";
    }


    @GetMapping("/api/greeting")
    public String greeting() {
        return "안녕하세요, 스프링 부트와 첫 API 개발을 축하해요! 🥳";
    }
}