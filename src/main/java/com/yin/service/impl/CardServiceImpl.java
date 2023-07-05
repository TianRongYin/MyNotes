package com.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yin.domain.Card;
import com.yin.domain.Result;
import com.yin.mapper.CardMapper;
import com.yin.service.CardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {
    @Resource
    CardMapper cardMapper;
    @Override
    public Result getByRegion(Integer region) {
        LambdaQueryWrapper<Card> qw = new LambdaQueryWrapper<Card>();
        qw.eq(Card::getRegion,region);
        List<Card> list = list(qw);
        Collections.reverse(list);
        return Result.success(list,region+"的全部数据");
    }

    @Override
    public Result selectByRegion(Integer region) {
        List<Card> list = cardMapper.selectByRegion(region);
        Collections.reverse(list);
        return Result.success(list,"测试");
    }

    @Override
    public Result getByUid(Integer uid) {
        LambdaQueryWrapper<Card> qw = new LambdaQueryWrapper<Card>();
        qw.eq(Card::getUId,uid);
        List<Card> list = list(qw);
        Collections.reverse(list);
        return Result.success(list,uid+"的全部数据");
    }
}
