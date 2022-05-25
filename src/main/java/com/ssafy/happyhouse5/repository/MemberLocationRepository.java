package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.MemberLocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberLocationRepository extends JpaRepository<MemberLocation, Long> {

    @Query("select l from MemberLocation l where l.member.id=:memberId")
    List<MemberLocation> findByMemberId(@Param("memberId") Long memberId);
}
