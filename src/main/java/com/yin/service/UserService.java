package com.yin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yin.domain.Result;
import com.yin.domain.User;

public interface UserService extends IService<User> {
     Result login(String username,String password);
     Result isEmpty(String username);
     Result isLogin(String token);
     String getName(Integer uid);
     void updateFaceById(Integer id,String face);
}
