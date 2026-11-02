
package com.myproject.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// username을 통해 회원 정보를 조회
    Optional<User> findByUsername(String username);    
    
    // 해당 username이 데이터베이스에 존재하는지 확인
    boolean existsByUsername(String username);
}
