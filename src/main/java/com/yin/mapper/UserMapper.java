package com.yin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yin.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


public interface UserMapper extends BaseMapper<User> {
    @Update("UPDATE user SET face = #{face} WHERE u_id = #{id}")
    void updateFaceById(@Param("id") Integer id, @Param("face") String face);
}
