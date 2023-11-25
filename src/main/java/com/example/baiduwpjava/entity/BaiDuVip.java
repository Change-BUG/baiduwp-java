package com.example.baiduwpjava.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiDuVip {

    /**
     * vip
     */
    String cluster;

    /**
     * vip 类型  vip-普通用户 svip-SVIP用户
     */
    String detail_cluster;

    /**
     * 开始时间
     */
    Long start_time;

    /**
     * 结束时间
     */
    Long end_time;

    /**
     * 状态 0:正常
     */
    Integer status;

}
