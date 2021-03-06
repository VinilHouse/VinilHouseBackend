package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByIdent(String ident);

    Optional<Member> findMemberByEmail(String email);
}
