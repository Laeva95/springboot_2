package com.mysite.sbb;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity								// 모든 웹 요청이 해당 설정 파일의 검사를 받음
@Configuration									// 스프링부트의 설정 파일임을 명시
@EnableMethodSecurity(prePostEnabled = true)	// Controller 메서드 단위로 권한 검사를 할 수 있음
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
        		// 로그인 하지 않아도 모든 요청에 접근 가능하도록 설정
        			(authorizeHttpRequests) ->
		        		authorizeHttpRequests
			        		.requestMatchers(new AntPathRequestMatcher("/**"))
			        		.permitAll())		
			 .csrf(
				// /h2-console/로 시작하는 모든 요청에 csrf 검증을 하지 않도록 설정
					 (csrf) ->
				 		csrf
					 		.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
			 .headers(
				// X-Frame-options 헤더를 DENY에서 SAMEORIGIN으로 수정
					 (headers) -> 
		 				headers
			 				.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
			 .formLogin(
				// 로그인 요청 url과 로그인 성공시 요청 url을 설정	
				// loginPage("/user/login"): GET 요청, POST 요청을 모두 처리. 
				// 							GET은 컨트롤러에서 개발자가 직접 처리, POST는 Spring Security가 자동으로 처리
					 (formLogin) -> 
				 		formLogin
					 		.loginPage("/user/login")
					 		.defaultSuccessUrl("/"))
			 .logout(
				// 로그아웃 요청 url과 로그아웃 성공시 요청 url, 사용자 세션 삭제 처리
					 (logout) -> 
				 		logout
			                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
			                .logoutSuccessUrl("/")
			                .invalidateHttpSession(true));			
        return http.build();
    }
	
	// 비밀번호를 암호화하기 위한 메소드
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	// 로그인 요청을 받았을 때 내부적으로 사용자 인증과 권한 부여 프로세스를 처리함
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}