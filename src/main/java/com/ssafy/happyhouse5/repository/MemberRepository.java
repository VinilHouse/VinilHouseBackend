package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
