
package com.myproject.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myproject.entity.User;
import com.myproject.repository.UserRepository;

// UserDetailsService: 스프링 시큐리티에서 로그인할 사용자의 정보를 조회하기 위해 제공하는 인터페이스
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 데이터베이스에서 유저를 찾는 메소드
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

    	// 계정을 조회하고 없다면 예외 발생
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "사용자를 찾을 수 없습니다."
                    )
                );

        // 스프링 시큐리티 인증에 사용 할 수 있도록 엔티티 정보를 변환
        // username: 로그인 id
        // password: 저장된 해시 비밀번호
        // authorities: 사용자 권한
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();
    }
}
