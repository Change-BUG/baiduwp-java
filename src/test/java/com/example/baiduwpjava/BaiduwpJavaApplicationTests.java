package com.example.baiduwpjava;

import com.example.baiduwpjava.entity.BaiDuDownloadLink;
import com.example.baiduwpjava.entity.BaiDuSign;
import com.example.baiduwpjava.entity.BaiDuUserData;
import com.example.baiduwpjava.entity.DataVoLink;
import com.example.baiduwpjava.util.BaiDuPanUtil;
import com.sun.org.apache.bcel.internal.generic.LOR;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

@Log4j2
@SpringBootTest
class BaiduwpJavaApplicationTests {

    static String surl = "1" + "TCKL3hQOUQRtqnbMhyr86w";    //分享链接/s/后的一部分
    static String pwd = "euhd";                             //提取码


    static BaiDuUserData baiDuUserData1 = new BaiDuUserData(
            "",
            ""
    );

    @Autowired
    private BaiDuPanUtil baiDuPanUtil;

    @Test
    public void demo03() {
        try {

            baiDuPanUtil.getUserInfo(baiDuUserData1.UserData());

            // 查询下载链接
            BaiDuSign sign = baiDuPanUtil.getSign(baiDuUserData1, surl);
//            System.out.println(sign);

            // 获取下载文件
//            DataVoLink listApi = BaiduPanUtil.getListApi(baiDuUserData, surl, pwd, "/软件/navicate16-taobao/mac/mac navicat16.2.x含redies", "1");
            DataVoLink listApi = baiDuPanUtil.getListApi(baiDuUserData1, surl, pwd, "", "1");
            listApi.getBaiDuFiles().forEach(System.out::println);

            // 获取链接 需要 的 数据
            BaiDuDownloadLink baiDuFile = new BaiDuDownloadLink();

            baiDuFile.setSign(sign.getSign());
            baiDuFile.setTimestamp(Long.valueOf(sign.getTimestamp()));

            baiDuFile.setSeckey(listApi.getBaiDuData().getSeckey());
            baiDuFile.setFsIdList(listApi.getFsIdList());
            baiDuFile.setShareid(listApi.getBaiDuData().getShareid());
            baiDuFile.setUk(listApi.getBaiDuData().getUk());

            log.info(baiDuFile);

            // 获取直链
            ArrayList<String> downloadLink = baiDuPanUtil.getDownloadLink(baiDuUserData1, baiDuFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
