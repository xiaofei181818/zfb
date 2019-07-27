package com.xiaofei.pay.comxiaofeipay.comm;

public class ResponseData {

    /**
     * 200 正常
     * 201 参数错误
     * 202 商户不存在
     * 203 系统异常，联系管理员
     */
    private String code;

    private String msg;

    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
