package com.xiaofei.pay.comxiaofeipay.service.impl;

import com.xiaofei.pay.comxiaofeipay.mapper.AliOrderMapper;
import com.xiaofei.pay.comxiaofeipay.bean.AliOrderBean;
import com.xiaofei.pay.comxiaofeipay.service.IAliOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IAliOrderServiceImpl implements IAliOrderService {

    @Autowired
    private AliOrderMapper orderMapper;

    /**
     *
     * @param bean
     * @return
     */
    @Override
    public Long add(AliOrderBean bean) {
        return orderMapper.add(bean);
    }

    @Override
    public void updateStatus(String out_trade_no) {
        orderMapper.updateStatus(out_trade_no);
    }

    @Override
    public List<AliOrderBean> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public int findStatusByNo(String no) {
        return orderMapper.findStatusByNo(no);
    }
}
