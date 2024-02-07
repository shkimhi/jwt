package com.cos.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 다운캐스팅
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //토큰 이름이 코스 일때만 컨트롤러로 진입하게 하기
        //시큐리티가 동작 전에 필터가 돌아야함.

        //토큰 cos 를 만들어야함. ( id,pw 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어주고 그걸 응답 해준다.)
        //요청 시 마다 header에 Authoriztaion에 value값으로 토큰을 가지고 오겠지?
        //그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증 하면됨 (RSA, HS256)
        if(req.getMethod().equals("POST")){
            System.out.println("POST 요청들어옴");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);
            System.out.println("필터 1");

            if(headerAuth.equals("cos")){
                chain.doFilter(req,res);
            }else{
                PrintWriter out = res.getWriter();
                out.print("인증실패 ");
            }
        }

    }
}
