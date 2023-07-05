package com.yin.controller;

import com.yin.service.CardService;
import com.yin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BootApplicationTests {
    @Resource
    UserService userService;
    @Resource
    CardService cardService;
    @Test
    void jwt(){
        System.out.println(userService.login("ytr","123456"));
    }
    @Test
    void mapper(){
        System.out.println(cardService.selectByRegion(1));
    }
}
