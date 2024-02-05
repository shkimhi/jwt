package com.cos.jwt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin 인증이 필요한 요청은 모두 거절됨. ( 로그인을 해야지만 하는 요청 )
@RestController
public class RestApiController {

    @GetMapping("home")
    public String home(){
        return "<h1>home</h1>";
    }
}
