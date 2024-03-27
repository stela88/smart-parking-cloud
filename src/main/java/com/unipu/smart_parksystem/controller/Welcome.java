package com.unipu.smart_parksystem.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {

    @Value("${welcome.message}")
    private String welcomeMesssage;

    @GetMapping("/")
    public String helloWorld() {
        return welcomeMesssage;
    }

}
