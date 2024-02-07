package com.cos.jwt.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("필터 3");
        chain.doFilter(request,response); // 계속 프로세스를 진행해야 함.

        //이런식으로하면 필터가 hi를 출력하고 끝남.
        //PrintWriter out = response.getWriter();
        //out.print("hi");


    }
}
