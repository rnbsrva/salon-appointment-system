package com.akerke.loggerlib.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("get")
    String get(){
        return ("hello world");
    }

}
