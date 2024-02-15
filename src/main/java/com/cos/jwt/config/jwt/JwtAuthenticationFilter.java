package com.cos.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

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
/*
            BufferedReader br = request.getReader();
            String input = null;
            while ((input = br.readLine()) != null){
                System.out.println(input);
            }
*/

            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // PrincipalDtailsService의 loadUserByUsername() 함수 실행된 후 정상이면 authentication이 리턴됨
            // DB에 있는 유저 정보와 일치 한다는 것.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            //authentication 객체가 Session 영역에 저장됨. >> 로그인이 되었다는 뜻
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨 : " + principalDetails.getUser().getUsername()); // 로그인 정상처리

            //authentication객체를 session에 저장을 해야하고 그 방법이 return 해주면 됨.
            //굳이 리턴을 해주는 이유는 ? >> 권한 관리를 시큐리티가 대신 해주기 때문에 편하기 때문.
            //굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음 >> 근데 단지 권한 처리 때문에 session을 넣어줌.
            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=========================================================");
        return null;
    }

    //attemptAuthentication실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨.
    //JWT 토큰을 만들어서 request요청한 사용자에게 JWT 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.out.println("successfulAuthentication 실행, 인증완료 : ");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();


        //토큰 생성 기본적으로 빌더 패턴 ( RSA 방식 아니고 , Hash암호 방식
        String jwtToken = JWT.create()
                .withSubject("cos") // 토큰 이름
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) //토큰 만료 시간
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("cos"));

        response.addHeader("Authorization","Bearer "+jwtToken);

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
