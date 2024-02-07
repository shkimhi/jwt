package com.cos.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        System.out.println("필터1");
        chain.doFilter(request,response);
        //이런식으로하면 필터가 hi를 출력하고 끝남.
        //PrintWriter out = response.getWriter();
        //out.print("hi");


    }
}
