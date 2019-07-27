package com.xiaofei.pay.comxiaofeipay.bean;

import org.springframework.stereotype.Component;

@Component
public class ShopBean {

    private Long id;
    private String name;
    // 商户号
    private String no;
    // 商户秘钥
    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
