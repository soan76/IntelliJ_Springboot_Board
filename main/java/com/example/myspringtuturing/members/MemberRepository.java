package com.example.myspringtuturing.members;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);// 24.09.23 : 증복 회원 방지 유효성 검사
    Optional<Member> findByUsername(String username);
}
