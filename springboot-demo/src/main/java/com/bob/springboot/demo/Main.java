package com.bob.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bob Jiang on 2016/10/11.
 */
@SpringBootApplication
@RestController
@RequestMapping("/demo")
public class Main {

    @RequestMapping("/index")
    public String index() {
        return "hello world";
    }

    @RequestMapping("/user/{userName}")
    public String post(@PathVariable("userName") String userName) {
        return String.format("hello, user %s, haha.", userName);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
