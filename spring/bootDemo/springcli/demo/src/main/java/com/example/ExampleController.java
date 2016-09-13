package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liulixiang on 16/9/9.
 */
@RestController
public class ExampleController {
    @RequestMapping("/")
    public String hello() {
        return "Hello, world";
    }
}
