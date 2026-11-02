package com.myproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(
	                "/signup",
	                "/login",
	                "/css/**",
	                "/js/**",
	                "/images/**"
	            ).permitAll()	// 로그인 하지 않고도 접속 가능한 주소
	            .anyRequest()	// 이외의 나머지 주소는 로그인 필수
	            .authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/login")			// 로그인 화면 페이지 url
	            .defaultSuccessUrl("/", true)	// 로그인 성공 후 이동 화면
	            .failureUrl("/login?error")		// 로그인 실패 후 이동 화면
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login?logout")	// 로그아웃 후 이동 화면
	            .permitAll()
	        );

	    return http.build();
	}
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
