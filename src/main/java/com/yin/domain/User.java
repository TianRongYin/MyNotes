package com.yin.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "u_id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String face;
}
