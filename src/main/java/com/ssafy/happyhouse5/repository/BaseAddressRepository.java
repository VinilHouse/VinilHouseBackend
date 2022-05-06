package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.BaseAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseAddressRepository extends JpaRepository<BaseAddress, String> {

}
