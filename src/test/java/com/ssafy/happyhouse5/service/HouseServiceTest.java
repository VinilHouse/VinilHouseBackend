package com.ssafy.happyhouse5.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.happyhouse5.dto.house.HouseInfoWithRankDto;
import com.ssafy.happyhouse5.entity.BaseAddress;
import com.ssafy.happyhouse5.entity.Favorite;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.entity.Member;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class HouseServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    HouseService houseService;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("Favorite 에 저장된 즐겨찾기 순위에 따른 HouseInfoWithRankDto: {HouseInfo + Count} 반환 테스트")
    void favoriteRankHouseInfoTest(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        Member member3 = new Member("member3");

        BaseAddress baseAddress = new BaseAddress("dong_temp");

        HouseInfo houseInfo1 = new HouseInfo("apt1", baseAddress);
        HouseInfo houseInfo2 = new HouseInfo("apt2", baseAddress);
        HouseInfo houseInfo3 = new HouseInfo("apt3", baseAddress);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        em.persist(baseAddress);

        em.persist(houseInfo1);
        em.persist(houseInfo2);
        em.persist(houseInfo3);

        Favorite favorite1 = new Favorite();
        favorite1.setMember(member1);
        favorite1.setHouseInfo(houseInfo1);

        Favorite favorite2 = new Favorite();
        favorite2.setMember(member1);
        favorite2.setHouseInfo(houseInfo2);

        Favorite favorite3 = new Favorite();
        favorite3.setMember(member1);
        favorite3.setHouseInfo(houseInfo3);

        Favorite favorite4 = new Favorite();
        favorite4.setMember(member2);
        favorite4.setHouseInfo(houseInfo1);

        Favorite favorite5 = new Favorite();
        favorite5.setMember(member2);
        favorite5.setHouseInfo(houseInfo2);

        Favorite favorite6 = new Favorite();
        favorite6.setMember(member3);
        favorite6.setHouseInfo(houseInfo1);

        em.persist(favorite1);
        em.persist(favorite2);
        em.persist(favorite3);
        em.persist(favorite4);
        em.persist(favorite5);
        em.persist(favorite6);

        Pageable pageable = PageRequest.of(0, 3);
        List<HouseInfoWithRankDto> houseInfos = houseService.findFavoriteRankHouseInfo(pageable);

        assertThat(houseInfos)
            .extracting("houseInfoResponseDto")
            .extracting("aptName")
            .containsExactly("apt1", "apt2", "apt3");
    }
}