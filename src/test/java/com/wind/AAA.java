package com.wind;


import javax.annotation.Resource;

public class AAA {
    /**
     * 状态码
     */
    @Resource
    private Integer code;
    /**
     * 消息说明
     */
    private String msg;

    /**
     * 系统当前时间,GTM时间戳
     */
    private Long timestamp;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
