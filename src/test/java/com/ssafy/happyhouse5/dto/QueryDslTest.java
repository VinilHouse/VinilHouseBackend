package com.ssafy.happyhouse5.dto;

import static com.ssafy.happyhouse5.dto.QTestEntity.testEntity;
import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QueryDslTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    void simpleTest(){
        TestEntity entity = new TestEntity("hello");
        em.persist(entity);

        List<TestEntity> fetch = queryFactory
            .selectFrom(testEntity)
            .fetch();

        assertThat(fetch)
            .extracting("name")
            .containsExactly("hello");
    }
}
