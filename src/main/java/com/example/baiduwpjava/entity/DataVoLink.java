package com.example.baiduwpjava.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVoLink {

    private ArrayList fsIdList;

    private BaiduData baiduData;

    private List<BaiDuFile> baiDuFiles;

    public BaiduData getBaiDuData() {
        return baiduData;
    }

}
