package com.yin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yin.domain.Card;
import com.yin.domain.Result;

public interface CardService extends IService<Card> {
    Result getByRegion(Integer cid);
    Result selectByRegion(Integer region);
    Result getByUid(Integer uid);
}
