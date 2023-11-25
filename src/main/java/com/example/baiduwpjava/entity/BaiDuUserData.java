package com.example.baiduwpjava.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiDuUserData {

    private String STOKEN;

    private String BDUSS;

    public String UserData() {
        return "STOKEN=" + STOKEN + ";BDUSS=" + BDUSS;
    }

}
