package com.example.baiduwpjava.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.baiduwpjava.entity.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class BaiDuPanUtil {

    @Autowired
    private BaiDuState baiDuState;

    static String UserAgent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36 Edg/110.0.1587.69";

    /**
     * 用户信息
     *
     * @param Cookie
     * @throws IOException
     */
    public void getUserInfo(String Cookie) throws IOException {
        String url = "https://pan.baidu.com/rest/2.0/xpan/nas?method=uinfo";
        Document document = Jsoup
                .connect(url)
                .ignoreContentType(true)
                .header("User-Agent", UserAgent)
                .header("Cookie", Cookie)
                .post();
        Element body = document.body();
        BaiDuUser baiDuUser = JSONObject.parseObject(body.text(), BaiDuUser.class);
        System.out.println(baiDuUser);
    }

    /**
     * 用户 vip 信息
     *
     * @param Cookie
     * @throws IOException
     */
    public void getVipInfo(String Cookie) throws IOException {
        String url = "https://pan.baidu.com/rest/2.0/membership/user?channel=chunlei";
        Document document = Jsoup
                .connect(url)
                .ignoreContentType(true)
                .header("User-Agent", UserAgent)
                .header("Cookie", Cookie)
                .data("method", "query")
                .post();
        Element body = document.body();
        JSONObject jsonStr = JSONObject.parseObject(body.text());
        JSONArray jsonArray = new JSONArray((List<Object>) jsonStr.get("product_infos"));

        for (Object o : jsonArray) {
            JSONObject jsonStr1 = JSONObject.parseObject(o.toString());
            // 只查询 是否是 vip 用户
            if (jsonStr1.getString("cluster").equals("vip")) {


                BaiDuVip baiDuVip = JSONObject.parseObject(String.valueOf(jsonStr1), BaiDuVip.class);
                System.out.println(baiDuVip);

                return;
            }
        }
    }

    /**
     * 获取 Sign
     *
     * @param Cookie Cookie
     * @param surl   链接
     * @return
     * @throws IOException
     */
    public BaiDuSign getSign(BaiDuUserData Cookie, String surl) throws IOException {
        surl = "?surl=" + surl + "&";
        String url =
                "https://pan.baidu.com/share/tplconfig" + surl + "fields=sign,timestamp&channel=chunlei&web=1&app_id=250528&clienttype=0";
        Document document = Jsoup
                .connect(url)
                .ignoreContentType(true)
                .header("User-Agent", "netdisk;pan.baidu.com")
                .cookie("Cookie", Cookie.UserData())
                .get();
        Element body = document.body();
        JSONObject jsonStr = JSONObject.parseObject(body.text());
        log.error(jsonStr);
        return JSONObject.parseObject(jsonStr.get("data").toString(), BaiDuSign.class);
    }

    /**
     * 解析 分享链接 的 文件
     *
     * @param Cookie Cookie
     * @param url    链接
     * @param pwd    密码
     * @param dir    目录
     * @param page   页数
     * @return
     * @throws IOException
     */
    public DataVoLink getListApi(BaiDuUserData Cookie, String url, String pwd, String dir, String page) throws
            IOException {
        String url1 = "https://pan.baidu.com/share/wxlist?channel=weixin&version=2.2.2&clienttype=25&web=1";
        Document document = Jsoup
                .connect(url1)
                .ignoreContentType(true)
                .header("User-Agent", "netdisk")
                .header("Referer", "https://pan.baidu.com/disk/home")
                .cookie("Cookie", Cookie.UserData())

                .data("shorturl", url)
                .data("dir", dir)
                .data("root", StringUtils.isNotBlank(dir) ? "0" : "1")
                .data("pwd", pwd)
                .data("page", page)
                .data("num", "1000")
                .data("order", "time")

                .post();

        Element body = document.body();
        JSONObject jsonStr = JSONObject.parseObject(body.text());

        if (!jsonStr.get("errno").toString().equals("0")) {
            log.error(jsonStr.get("errno").toString());
            throw new RuntimeException(baiDuState.listError(jsonStr.get("errno").toString()));
        }

        // 获取基本信息
        BaiduData baiduData = JSONObject.parseObject(jsonStr.get("data").toString(), BaiduData.class);

        JSONObject jsonStr2 = JSONObject.parseObject(jsonStr.get("data").toString());

        JSONArray jsonArray = new JSONArray((List<Object>) jsonStr2.get("list"));

        if (jsonArray.size() == 0) {
            throw new RuntimeException("获取文件列表失败");
        }

        List<BaiDuFile> list = new ArrayList<>();
        ArrayList fsIdList = new ArrayList<>();

        for (Object o : jsonArray) {
            JSONObject jsonStr3 = JSONObject.parseObject(o.toString());
            list.add(JSONObject.parseObject(jsonStr3.toString(), BaiDuFile.class));
            fsIdList.add(jsonStr3.getString("fs_id"));
        }

        return new DataVoLink(fsIdList, baiduData, list);
    }

    /**
     * 获取下载链接
     *
     * @param Cookie Cookie
     * @return
     * @throws IOException
     */
    public ArrayList<String> getDownloadLink(BaiDuUserData Cookie, BaiDuDownloadLink baiDuDownloadLink) throws
            IOException {
        // https://pan.baidu.com/api/sharedownload?channel=chunlei&clienttype=0&web=1&app_id=250528&sign=43972457b1b762b358d5540e163f2cfdabceb6d6&timestamp=1700881724
        String url = "https://pan.baidu.com/api/sharedownload";
        url += "?app_id=" + "250528" +
                "&channel=" + "chunlei" +
                "&clienttype=" + "12" +
                "&sign=" + baiDuDownloadLink.getSign() +
                "&timestamp=" + baiDuDownloadLink.getTimestamp() +
                "&web=" + "1";
        log.info(url);

        baiDuDownloadLink.setSeckey(URLDecoder.decode(baiDuDownloadLink.getSeckey(), StandardCharsets.UTF_8.name()));

        Document document = Jsoup
                .connect(url)
                .ignoreContentType(true)
                .header("User-Agent", UserAgent)
                .header("Referer", "https://pan.baidu.com/disk/home")
                .header("Host", "pan.baidu.com")
                .header("Cookie", Cookie.UserData())

                .data("encrypt", "1")
                .data("extra", "{" + "\"sekey\"" + ":" + "\"" + baiDuDownloadLink.getSeckey() + "\"" + "}")
                .data("product", "share")
                .data("timestamp", baiDuDownloadLink.getTimestamp().toString())
                .data("uk", baiDuDownloadLink.getUk())
                .data("primaryid", baiDuDownloadLink.getShareid())
                .data("fid_list", "[" + baiDuDownloadLink.getFsIdList().get(0) + "]")

                .post();

        // 没有 sekey 参数就 118, -20出现验证码

        Element body = document.body();
        JSONObject jsonStr = JSONObject.parseObject(body.text());
        log.info(jsonStr);

        if (!jsonStr.get("errno").toString().equals("0")) {
            log.error(jsonStr.get("errno").toString());
            throw new RuntimeException(baiDuState.downloadError(jsonStr.get("errno").toString()));
        }

        JSONArray jsonArray = new JSONArray((List<Object>) jsonStr.get("list"));
        ArrayList<String> list = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonStr1 = JSONObject.parseObject(o.toString());

            String dlink = getReal(Cookie.getBDUSS(), jsonStr1.get("dlink").toString());
            System.out.println("得到真实地址" + dlink);

            list.add(dlink);
        }
        return list;
    }

    /**
     * 获得下载链接
     *
     * @param BDUSS BDUSS
     * @param dlink pcs地址
     * @return
     * @throws IOException
     */
    public String getReal(String BDUSS, String dlink) throws IOException {
        Connection.Response response = Jsoup
                .connect(dlink)
                .ignoreContentType(true)
                .followRedirects(true)
                .header("User-Agent", "LogStatistic")
                .cookie("BDUSS", BDUSS)
                .execute();
        return String.valueOf(response.url());
    }

}