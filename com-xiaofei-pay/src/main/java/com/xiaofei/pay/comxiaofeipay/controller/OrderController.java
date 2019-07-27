package com.xiaofei.pay.comxiaofeipay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.xiaofei.pay.comxiaofeipay.comm.AliResultContentData;
import com.xiaofei.pay.comxiaofeipay.comm.AliResultData;
import com.xiaofei.pay.comxiaofeipay.comm.ResponseData;
import com.xiaofei.pay.comxiaofeipay.model.AliFacePayBean;
import com.xiaofei.pay.comxiaofeipay.bean.AliOrderBean;
import com.xiaofei.pay.comxiaofeipay.model.AlipayBean;
import com.xiaofei.pay.comxiaofeipay.bean.ShopBean;
import com.xiaofei.pay.comxiaofeipay.service.IAliOrderService;
import com.xiaofei.pay.comxiaofeipay.service.IShopService;
import com.xiaofei.pay.comxiaofeipay.service.impl.Alipay;
import com.xiaofei.pay.comxiaofeipay.utitls.MD5Util;
import com.xiaofei.pay.comxiaofeipay.utitls.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    Alipay alipay;

    @Autowired
    IAliOrderService orderService;

    @Autowired
    IShopService shopService;

    /**
     * 手机网站支付
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    @PostMapping(value = "alipay")
    @ResponseBody
    public String alipay(AlipayBean alipayBean){

        String no = OrderUtils.getOrderNo();
        alipayBean.setOut_trade_no(no);

        /**
         * 订单号out_trade_no
         * 订单名称 subject
         * 金额 total_amount
         * 描述，可空 body
         *
         */
        // 下单数据库
        AliOrderBean bean = new AliOrderBean();
        bean.setOut_trade_no(alipayBean.getOut_trade_no());
        bean.setCreate(OrderUtils.getTime());
        bean.setAmount(alipayBean.getTotal_amount());
        orderService.add(bean);
        log.info("==========  订单号：" + no + "已下单 ===================");

        try {
            return alipay.pay(alipayBean);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "支付异常";
        }
    }

    /**
     * 当面付（条形码，二维码商家）
     * 生成二维码：
     *  http://qr.liantu.com/api.php?text=http://www.baidu.com
     *  http://b.bshare.cn/barCode?site=weixin&url=http://www.baidu.com
     *  http://api.k780.com:88/?app=qr.get&data=http://www.baidu.com
     * @return
     */
    @PostMapping(value = "aliface")
    @ResponseBody
    public ResponseData fac(String amount,String sign,String mertNo,String notifyUrl,String outTradeNo){

        AliFacePayBean bean = null;
        // 查是否有商户，查商户的秘钥
        ResponseData responseData = new ResponseData();
        if (mertNo == null || mertNo.equals("")) {
            // 参数不齐
            responseData.setCode("201");
            responseData.setMsg("参数错误");
        }else {
            ShopBean shop = shopService.findByNo(mertNo);
            if(shop == null){
                // 商户不存在
                responseData.setCode("202");
                responseData.setMsg("商户不存在");
            }else {
                // 判断传过来的sign是否正确
                String reqStr =
                        "mert_no=" + mertNo +
                        "&notify_url=" + notifyUrl +
                        "&out_trade_no=" + outTradeNo +
                        "&total_amount=" + amount +
                        "&key=" + shop.getKey();
                Boolean isTrue = MD5Util.check(reqStr,sign);
                if (isTrue){
                    // 参数正确，下单
                    try {
                        bean = new AliFacePayBean();
                        bean.setTotal_amount(amount);
                        bean.setOut_trade_no(outTradeNo);
                        bean.setSubject("扫码支付");
                        String result = alipay.aliFace(bean);
                        if (result != null && !result.equals("")){
                            AliResultData da = JSON.parseObject(result, AliResultData.class);
                            AliResultContentData dd = JSON.parseObject(da.getAlipay_trade_precreate_response(),AliResultContentData.class);
                            if (dd.getCode().equals("10000")){
                                // 下单成功，返回地址
                                // 数据库记录
                                AliOrderBean b = new AliOrderBean();
                                b.setOut_trade_no(outTradeNo);
                                b.setCreate(OrderUtils.getTime());
                                b.setAmount(amount);
                                b.setNotify_url(notifyUrl); // 平台回调地址
                                b.setChannel(2);
                                b.setMert_no(mertNo);
                                orderService.add(b);
                                log.info("==========  订单号：" + bean.getOut_trade_no() + "已下单, ===================");

                                responseData.setCode("200");
                                responseData.setData(dd.getQr_code());
                            }else {
                                responseData.setCode("203");
                                responseData.setMsg("系统异常，联系管理员:预下单失败");
                            }
                        }
                    } catch (AlipayApiException e) {
                        responseData.setCode("203");
                        responseData.setMsg("系统异常，联系管理员:" + e.getMessage());
                    }
                }else {
                    // 参数不对
                    responseData.setCode("201");
                    responseData.setMsg("参数错误");
                }
            }
        }
        return responseData;
    }

    /**
     * 支付宝回调
     * @param request
     */
    @PostMapping(value = "notify1")
    @ResponseBody
    public void notify1(HttpServletRequest request){
        try {
            String out_trade_no = "";
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            if (trade_status != null && !trade_status.equals("") && trade_status.equals("TRADE_SUCCESS")){
                out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
                log.info("=============  订单号：" + out_trade_no + "支付成功回调 ====================");
                // 改变订单的状态
                // 如果状态已经改变的无需重新修改，直接放行，支付宝好像会几次的回调
                int status = orderService.findStatusByNo(out_trade_no);
                if (status == 0){
                    orderService.updateStatus(out_trade_no);
                    log.info("==============  订单号：" + out_trade_no + "支付状态已修改 ==================");
                }else {
                    log.info("================  无用的回调，支付状态已修改过 ==============================");
                }
            }else if (trade_status != null && !trade_status.equals("") && trade_status.equals("TRADE_CLOSED")){
                log.info("==============  订单号：" + out_trade_no + "交易关闭 ==================");
            }else {
                log.info("==============  未知支付宝回调 ==================");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询所有订单
     * @return
     */
    @GetMapping(value = "findAll")
    @ResponseBody
    public List<AliOrderBean> findAll(){
        return orderService.findAll();
    }

}
