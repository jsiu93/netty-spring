package com.xzh.nettyspring.example;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Authur: joshuasiu
 * @Date: 2019-06-05 16:09
 * @Description:
 */
@Data
public class RequestHandler {

    public String handle(String request) {
        return "hello " + request;
    }
}
