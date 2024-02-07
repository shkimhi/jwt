package com.cos.jwt.config.jwt;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 필터가 존재함.
// "/login" 요청하여 Username, password 전송 하면 (post) UsernamePasswordAuthenticationFilter가 동작한다.
// security config에서 formLoing.disable() 했기 때문에 동작하지않음
// 작동 시킬려면 JwtAuthenticationFilter 이 필터를 security 필터에 등록 하면 됨.
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JwtAuthenticationFilter(Au) {
    }
}
