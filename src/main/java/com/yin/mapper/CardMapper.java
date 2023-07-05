package com.yin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yin.domain.Card;

import java.util.List;

public interface CardMapper extends BaseMapper<Card> {
    List<Card> selectByRegion(Integer region);
}
