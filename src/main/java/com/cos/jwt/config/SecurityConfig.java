package com.cos.jwt.config;

import com.cos.jwt.config.jwt.JwtAuthenticationFilter;
//import com.cos.jwt.config.jwt.JwtAuthorizationFilter;
import com.cos.jwt.config.jwt.JwtAuthorizationFilter;
import com.cos.jwt.filter.MyFilter1;
import com.cos.jwt.filter.MyFilter3;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class); //시큐리티 필터가 filterChain이 내가 만든 filter 보다 먼저 작동함.
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다는 설정
        .and()
                .addFilter(corsFilter) // @CrossOrigin ( 인증X ) , 시큐리티 필터에 등록 (인증 O)
                .formLogin().disable() // 폼 로그인 사용 X
                .httpBasic().disable() // 기본적인 HTTP 로그인 방식 사용 X
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) //Authentication Manager
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository)) //
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll(); // 나머지 요청은 모두 권한 없이 접근 가능.
    }
}

