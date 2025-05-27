package com.example.efubtoy.user.repository;

import com.example.efubtoy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // 회원 닉네임으로 조회
    //Optional<User> findByNickname(String nickname);
}
