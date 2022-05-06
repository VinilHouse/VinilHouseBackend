package com.ssafy.happyhouse5.dao;

import com.ssafy.happyhouse5.entity.BaseAddress;
import com.ssafy.happyhouse5.repository.BaseAddressRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BaseAddressRepositoryTest {

    @Autowired
    BaseAddressRepository baseAddressRepository;

    @Test
    void simpleBaseAddressTest(){
        List<BaseAddress> all = baseAddressRepository.findAll();
        Assertions.assertThat(all.size()).isNotEqualTo(0);
        for (BaseAddress address : all) {
            System.out.println(address.getDongName());
        }
    }
}