package com.xiaofei.pay.comxiaofeipay.service;

import com.xiaofei.pay.comxiaofeipay.bean.AliOrderBean;

import java.util.List;

public interface IAliOrderService {

    public Long add(AliOrderBean bean);

    public void updateStatus(String out_trade_no);

    public List<AliOrderBean> findAll();

    public int findStatusByNo(String no);
}
