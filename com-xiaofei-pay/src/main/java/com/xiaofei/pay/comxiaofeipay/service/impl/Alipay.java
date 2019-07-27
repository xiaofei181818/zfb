package com.xiaofei.pay.comxiaofeipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.xiaofei.pay.comxiaofeipay.model.AliFacePayBean;
import com.xiaofei.pay.comxiaofeipay.model.AlipayBean;
import com.xiaofei.pay.comxiaofeipay.model.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Alipay {
    /**
     * 支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    @Autowired
    AlipayProperties alipayProperties;

    public String pay(AlipayBean alipayBean) throws AlipayApiException {
        // 1、获得初始化的AlipayClient
        String serverUrl = alipayProperties.getGatewayUrl();
        String appId = alipayProperties.getApp_id();
        String privateKey = alipayProperties.getPrivateKey();
        String format = "json";
        String charset = alipayProperties.getCharset();
        String alipayPublicKey = alipayProperties.getPublicKey();
        String signType = alipayProperties.getSign_type();
        String returnUrl = alipayProperties.getReturn_url();
        String notifyUrl = alipayProperties.getNotify_url();
        String result = "";
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        // pc/手机支付
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(returnUrl);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notifyUrl);
        //账户订单号
//        String out_trade_no = new Date().getTime()+ UUID.randomUUID().toString();
        alipayBean.setOut_trade_no(alipayBean.getOut_trade_no());
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        // 3、请求支付宝进行付款，并获取支付结果
        result = alipayClient.pageExecute(alipayRequest).getBody();
        // 返回付款信息
        return result;
    }

    public String aliFace(AliFacePayBean bean) throws AlipayApiException {
        String serverUrl = alipayProperties.getGatewayUrl();
        String appId = alipayProperties.getApp_id();
        String privateKey = alipayProperties.getPrivateKey();
        String format = "json";
        String charset = alipayProperties.getCharset();
        String alipayPublicKey = alipayProperties.getPublicKey();
        String signType = alipayProperties.getSign_type();
        String returnUrl = alipayProperties.getReturn_url();
        String notifyUrl = alipayProperties.getNotify_url();
        String sell_id = alipayProperties.getSell_id();
        String result = "";
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        // 当面付
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);
        bean.setSell_id(sell_id);
        request.setBizContent(JSON.toJSONString(bean)); //设置业务参数
        AlipayTradePrecreateResponse resp = alipayClient.execute(request);
        result = resp.getBody();
        return result;
    }


}
