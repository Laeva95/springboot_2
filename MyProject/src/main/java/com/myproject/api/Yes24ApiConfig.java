
package com.myproject.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Yes24ApiConfig {

	// @Bean: 메서드가 반환한 객체를 스프링 컨테이너에 등록
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
