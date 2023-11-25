package com.example.baiduwpjava.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiDuUser {

    /**
     * 头像
     */
    String avatar_url;

    /**
     * 百度名称
     */
    String baidu_name;

    /**
     * 网盘名称
     */
    String netdisk_name;

    /**
     * 0: 普通用户 1: 会员 2: 超级会员
     */
    Integer vip_type;

}