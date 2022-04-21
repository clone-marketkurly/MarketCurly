package com.clone.marketcurly.repository;

import com.clone.marketcurly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /* JWT로그인 시 username으로 정보를 조회 후 비밀번호 비교시 사용 */
    Optional<User> findByUsername(String username);

    /* 유저아이디 중복검사 */
    boolean existsByUsername(String username);

}
