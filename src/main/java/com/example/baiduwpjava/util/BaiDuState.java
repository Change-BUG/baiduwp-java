package com.example.baiduwpjava.util;

import org.springframework.stereotype.Component;

@Component
public class BaiDuState {


    /**
     * 解析异常
     *
     * @param json
     * @return
     */
    public String listError(String json) {
        String type = "";

        switch (json) {
            case "mis_105":
                type = "你所解析的文件不存在~";
                break;
            case "mispw_9":
                type = "提取码错误";
                break;
            case "mispwd-9":
                type = "提取码错误";
                break;
            case "-130":
                type = "提取码错误";
                break;
            case "mis_2":
                type = "不存在此目录";
                break;
            case "mis_4":
                type = "不存在此目录";
                break;
            case "5":
                type = "不存在此分享链接或提取码错误";
                break;
            case "3":
                type = "此链接分享内容可能因为涉及侵权、色情、反动、低俗等信息，无法访问！";
                break;
            case "0":
                type = "啊哦，你来晚了，分享的文件已经被删除了，下次要早点哟。";
                break;
            case "10":
                type = "啊哦，来晚了，该分享文件已过期";
                break;
            case "8001":
                type = "普通账号可能被限制，请检查普通账号状态";
                break;
            case "9013":
                type = "普通账号被限制，请检查普通账号状态";
                break;
            case "9019":
                type = "普通账号 Cookie 状态异常，请检查：账号是否被限制、Cookie 是否过期（前往网站设置页面修改）";
                break;
            case "999":
                type = "错误" + json;
                break;
            default:
                type = "未知错误" + json;
        }

        return type;
    }

    /**
     * 下载异常
     *
     * @param json
     * @return
     */
    public String downloadError(String json) {
        String type = "";

        switch (json) {
            case "999":
                type = "请求错误" + "请求百度网盘服务器出错，请检查网络连接或重试";
                break;
            case "-20":
                type = "触发验证码(-20)" + "请等待一段时间，再返回首页重新解析。";
                break;
            case "-9":
                type = "文件不存在(-9)" + "请返回首页重新解析。";
                break;
            case "-6":
                type = "账号未登录(-6)" + "请检查普通账号是否正常登录。";
                break;
            case "-1":
                type = "文件违规(-1)" + "您下载的内容中包含违规信息";
                break;
            case "2":
                type = "下载失败(2)" + "下载失败，请稍候重试";
                break;
            case "112":
                type = "链接超时(112)" + "获取链接超时，每次解析列表后只有5min有效时间，请返回首页重新解析。";
                break;
            case "113":
                type = "传参错误(113)" + "获取失败，请检查参数是否正确。";
                break;
            case "116":
                type = "链接错误(116)" + "该分享不存在";
                break;
            case "118":
                type = "没有下载权限(118)" + "没有下载权限，请求百度服务器时，未传入sekey参数或参数错误。";
                break;
            case "110":
                type = "服务器错误(110)" + "服务器错误，可能服务器IP被百度封禁，请切换 IP 或更换服务器重试。";
                break;
            case "121":
                type = "服务器错误(121)" + "你选择操作的文件过多，减点试试吧";
                break;
            case "8001":
                type = "普通账号错误(8001)" + "普通账号可能被限制，请检查普通账号状态";
                break;
            case "9013":
                type = "普通账号错误(9013)" + "普通账号被限制，请检查普通账号状态";
                break;
            case "9019":
                type = "普通账号错误(9019)" + "普通账号 Cookie 状态异常，请检查：账号是否被限制、Cookie 是否过期（前往网站设置页面修改）";
                break;
            default:
                type = "未知错误" + json;
        }

        return type;
    }

    /**
     * 获取真实链接异常
     *
     * @param json
     * @return
     */
    public String realLinkError(String json) {
        String type = "";

        switch (json) {
            case "8001":
                type = "SVIP 账号可能被限制，请检查 SVIP 的 Cookie 是否设置正确且有效";
                break;
            case "9013":
                type = "SVIP 账号被限制，请检查更换 SVIP 账号";
                break;
            case "9019":
                type = "SVIP 账号可能被限制，请检查 SVIP 的 Cookie 是否设置正确且有效";
                break;
            case "31360":
                type = "下载链接超时，请刷新页面重试。若重试后仍报错，请检查普通帐号 Cookie 是否过期";
                break;
            case "31362":
                type = "下载链接签名错误，请检查 UA 是否正确";
                break;
            case "999":
                type = "错误" + json;
                break;
            default:
                type = "获取下载链接失败" + "未知错误" + json;
        }

        return type;
    }

}
