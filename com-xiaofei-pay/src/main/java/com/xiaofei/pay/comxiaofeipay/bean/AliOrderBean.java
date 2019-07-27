package com.xiaofei.pay.comxiaofeipay.bean;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 支付宝订单实体
 */
@Component
public class AliOrderBean {

    private Long id;

    // 下单时间
    private String create;

    // 0：支付宝  1：微信
    private int channel;

    // 交易金额
    private String amount;

    private String out_trade_no;

    /** 支付状态 0：未支付  1：已支付 */
    private int status;

    // 异步通知地址
    private String notify_url;

    // 异步回调
    private int call_back;

    // 回调次数，3次后就不进行回调
    private int call_no;

    //商户号
    private String mert_no;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public int getCall_back() {
        return call_back;
    }

    public void setCall_back(int call_back) {
        this.call_back = call_back;
    }

    public int getCall_no() {
        return call_no;
    }

    public void setCall_no(int call_no) {
        this.call_no = call_no;
    }

    public String getMert_no() {
        return mert_no;
    }

    public void setMert_no(String mert_no) {
        this.mert_no = mert_no;
    }
}
