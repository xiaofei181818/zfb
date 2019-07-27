package com.xiaofei.pay.comxiaofeipay.service.impl;

import com.xiaofei.pay.comxiaofeipay.mapper.ShopMapper;
import com.xiaofei.pay.comxiaofeipay.bean.ShopBean;
import com.xiaofei.pay.comxiaofeipay.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IShopServiceImpl implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public ShopBean findByNo(String mertNo) {
        return shopMapper.findByNo(mertNo);
    }
}
