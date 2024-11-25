package com.nilsw13.springreact.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TestController {


    @GetMapping("/test")
    public String test() {
        log.info("test endpoint called");
        return "Backend is connected";
    }
}
