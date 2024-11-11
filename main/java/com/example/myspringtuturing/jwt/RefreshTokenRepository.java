package com.example.myspringtuturing.jwt;

import com.example.myspringtuturing.members.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenDetail(String token);
    Optional<RefreshToken> findByMember(Member users);
}
