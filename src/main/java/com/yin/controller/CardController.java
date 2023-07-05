package com.yin.controller;

import com.yin.domain.Card;
import com.yin.domain.Result;
import com.yin.service.CardService;
import com.yin.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/card")
public class CardController {
    @Resource
    private CardService cardService;
    @Resource
    private UserService userService;
    @PostMapping("/form")
    public Result postForm(@RequestAttribute("uid")Integer id,@RequestAttribute("name")String name,@RequestBody Card card){
        card.setUId(id);
        card.setName(name);
        return cardService.save(card) ? Result.success("添加成功"):Result.fail("添加失败");
    }
    @GetMapping("/getByRegion/{cid}")
    public Result getByRegion(@PathVariable Integer cid){
        return cardService.getByRegion(cid);
    }
    @GetMapping("/getByUid")
    public Result getByUid(@RequestAttribute("uid")Integer uid){
        return cardService.getByUid(uid);
    }
    @DeleteMapping("/deleteById/{cid}")
    public void deleteById(@PathVariable Integer cid){
        cardService.removeById(cid);
    }
}
