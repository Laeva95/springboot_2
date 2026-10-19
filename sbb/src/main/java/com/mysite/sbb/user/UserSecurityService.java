package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    
    /*
     	UserDetailsService 인터페이스
     	loadUserByUsername() 메소드를 구현하기 위한 인터페이스
     
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	// 입력한 username과 일치하는 데이터가 db에 존재하는지 조회
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        // 데이터가 없을 경우 예외 발생
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        // Optional에서 SiteUser 객체 꺼내기
        SiteUser siteUser = _siteUser.get();
        
        // 권한 목록 만들기
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // username이 "admin"일 경우
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
        	// username이 "admin"이 아닐 경우
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        // username, password, 권한 값을 반환함
        // 스프링 시큐리티가 반환 받은 값과 사용자가 입력한 값이 일치하는지 검사하는 기능 수행
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
