
package com.myproject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.dto.SignupForm;
import com.myproject.entity.User;
import com.myproject.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 메소드
    @Transactional
    public void register(SignupForm form) {

    	// id가 데이터베이스에 존재하는지 확인
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException(
                    "이미 사용 중인 로그인 ID입니다.");
        }

        // 비밀번호 해싱
        String encodedPassword =
                passwordEncoder.encode(form.getPassword());

        // 새 User 엔티티 생성
        User user = new User(
                form.getUsername(),
                encodedPassword
        );

        // 데이터베이스에 저장
        userRepository.save(user);
    }
}
