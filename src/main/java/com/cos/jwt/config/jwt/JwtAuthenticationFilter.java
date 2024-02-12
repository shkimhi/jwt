package com.cos.jwt.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 필터가 존재함.
// "/login" 요청하여 Username, password 전송 하면 (post) UsernamePasswordAuthenticationFilter가 동작한다.
// security config에서 formLoing.disable() 했기 때문에 동작하지않음
// 작동 시킬려면 JwtAuthenticationFilter 이 필터를 security 필터에 등록 하면 됨.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    //login 요청을 하면 로그인 시도를 위해 실행되는 함수.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println( " JwtAuthenticationFilter : 로그인 시도 중 ");

        // 1. username, password 받아서
        // 2. 정상인지 로그인 시도를 해본다. authenticationManager로 로그인 시도를 하면 ( PrincipalDetailsService 호출 -> loadUserByUsername() 함수 실행)

        // 3. PrincipalDetails를 세션에 담고. (PrincipalDetails를 세션에 담는 이유 ? : 시큐리티 권한관리를 위해서 ! )
        // 4. JWT 토큰을 만들어서 응답 하면 됨.

        try {
            BufferedReader br = request.getReader();
            String input = null;
            while ((input = br.readLine()) != null){
                System.out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=========================================================");

        return super.attemptAuthentication(request, response);
    }
}
