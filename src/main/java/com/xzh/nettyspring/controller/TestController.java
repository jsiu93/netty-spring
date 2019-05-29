package com.xzh.nettyspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Authur: joshuasiu
 * @Date: 2019-05-29 10:48
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping("test")
    public String test1() {
        return "ok";
    }
}
