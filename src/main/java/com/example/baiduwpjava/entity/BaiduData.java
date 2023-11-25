package com.example.baiduwpjava.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiduData {

    /**
     * 标题
     */
    private String title;

    /**
     * seckey
     */
    private String seckey;

    /**
     * 分享Id
     */
    private String shareid;

    /**
     * uk
     */
    private String uk;

}