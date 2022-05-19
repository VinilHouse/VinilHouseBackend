package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.Favorite;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("select f from Favorite f where f.member=:member and f.houseInfo=:houseInfo ")
    Optional<Favorite> findByMemberAndHouseInfo(
        @Param("member") Member member, @Param("houseInfo") HouseInfo houseInfo);
}
