package com.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yin.domain.Result;
import com.yin.domain.User;
import com.yin.exception.NotLoginException;
import com.yin.mapper.UserMapper;
import com.yin.service.UserService;
import com.yin.utils.JwtUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public Result login(String username,String password) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUsername,username).eq(User::getPassword,password);
        User user = getOne(qw);
        if(user !=null){
            Map<String,Object> claims = new HashMap<String,Object>();
            claims.put("uid",user.getId());
            claims.put("name",user.getName());
            return Result.success(user.getFace(),JwtUtils.generateJwt(claims));
        }
        else return Result.fail("账号或密码错误");
    }
    @Override
    public Result isEmpty(String username) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<User>();
        qw.eq(User::getUsername,username);
        return getOne(qw)==null ? Result.success("账户不存在"):Result.fail("账户已存在");
    }
    @Override
    public Result isLogin(String token) {
        try{
            JwtUtils.parseJwt(token);
        }
        catch (Exception ex){
            throw new NotLoginException(502,"登录信息过期");
        }
        return Result.success("已经登录");
    }
    @Override
    public String getName(Integer uid) {
        return getById(uid).getName();
    }
    @Override
    public void updateFaceById(Integer id,String face) {
        userMapper.updateFaceById(id,face);
    }
}
