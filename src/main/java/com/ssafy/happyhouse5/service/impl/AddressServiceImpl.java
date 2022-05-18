package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.entity.BaseAddress;
import com.ssafy.happyhouse5.repository.BaseAddressRepository;
import com.ssafy.happyhouse5.service.AddressService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {

    private final BaseAddressRepository baseAddressRepository;

    public List<BaseAddress> getAllAddresses(){
        return baseAddressRepository.findAll();
    }
}
