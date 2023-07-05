package com.yin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@TableName("card")
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @TableId
    private Integer cId;
    private Integer uId;
    private String name;
    private String vx;
    private String phone;
    private String title;
    private String region;
    private Date date;
    private String description;
}
