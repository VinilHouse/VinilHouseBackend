package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.dto.common.Response.*;

import com.ssafy.happyhouse5.dto.baseaddress.BaseAddressDto;
import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.service.AddressService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/addresses")
@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<Response> getAllAddresses(){
        List<BaseAddressDto> dtoList = addressService.getAllAddresses().stream()
            .map(BaseAddressDto::new)
            .collect(Collectors.toList());

        if(dtoList.size() == 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(success(dtoList));
    }
}
