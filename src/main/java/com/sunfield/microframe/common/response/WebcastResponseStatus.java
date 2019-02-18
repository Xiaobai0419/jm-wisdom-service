package com.sunfield.microframe.common.response;

public enum WebcastResponseStatus {

    //成功
    SUCCESS,
    //失败
    FAIL,
    //无数据
    NO_DATA,
    //ID为空
    ID_NULL,
    //系统繁忙
    BUSY,
    //无权限
    FORBIDEN,
    //当前无直播
    NO_WEBCAST,
    //直播非未来时间段
    PAST,
    //开始时间晚于结束时间
    BEGIN_AFTER_END,
    //与当前直播冲突
    CONFLICT;

    public static String getStatus(WebcastResponseStatus rs){
        switch (rs) {
            case SUCCESS:
                return "SUCCESS";
            case FAIL:
                return "FAIL";
            case NO_DATA:
                return "NO_DATA";
            case ID_NULL:
                return "ID_NULL";
            case BUSY:
                return "BUSY";
            case FORBIDEN:
                return "FORBIDEN";
            case NO_WEBCAST:
                return "NO_WEBCAST";
            case PAST:
                return "PAST";
            case BEGIN_AFTER_END:
                return "BEGIN_AFTER_END";
            case CONFLICT:
                return "CONFLICT";
            default:
                return "UNKNOWN";
        }
    }

    public static String getMsg(WebcastResponseStatus rs){
        switch (rs) {
            case SUCCESS:
                return "请求成功";
            case FAIL:
                return "请求失败";
            case NO_DATA:
                return "无返回数据";
            case ID_NULL:
                return "ID为空";
            case BUSY:
                return "系统繁忙";
            case FORBIDEN:
                return "无权限";
            case NO_WEBCAST:
                return "当前无直播";
            case PAST:
                return "直播时间段应在还未开始的时间";
            case BEGIN_AFTER_END:
                return "开始时间应早于结束时间";
            case CONFLICT:
                return "与进行中直播冲突，请设置禁用或更改直播时间";
            default:
                return "未知错误";
        }
    }
}
