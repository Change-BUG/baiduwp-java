package com.example.baiduwpjava.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiDuDownloadLink {

    /**
     * 签名
     */
    private String sign;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * seckey
     */
    private String seckey;

    /**
     * 分享文件列表
     */
    private ArrayList fsIdList;

    /**
     * 分享Id
     */
    private String shareid;

    /**
     * uk
     */
    private String uk;

}