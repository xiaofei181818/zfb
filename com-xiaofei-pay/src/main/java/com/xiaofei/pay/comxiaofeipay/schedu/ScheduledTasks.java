package com.xiaofei.pay.comxiaofeipay.schedu;

import com.xiaofei.pay.comxiaofeipay.bean.AliOrderBean;
import com.xiaofei.pay.comxiaofeipay.mapper.AliOrderMapper;
import com.xiaofei.pay.comxiaofeipay.utitls.HttpRequestUtil;
import com.xiaofei.pay.comxiaofeipay.utitls.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异步回调平台
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private AliOrderMapper aliOrderMapper;

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        // 进行回调 修改回调状态call_back    回调次数call_no
        log.info("============  上分回调调度任务执行中  =================");
        Map<String,Object> param = null;
        AliOrderBean orderBean = null;
        List<AliOrderBean> l = aliOrderMapper.findCallOrder();
        if (l.size() > 0 && l != null){
            // http 访问平台
            log.info("=====================  有未回调的订单，订单数：" + l.size()+  " ===========================");
            param = new HashMap<>();
            for (AliOrderBean b : l){
                param.put("mertNo",b.getMert_no());
                param.put("outTradeNo",b.getOut_trade_no());
                param.put("amount",b.getAmount());
                param.put("status",b.getStatus());// 支付状态
                // sign处理
                String reqStr =
                        "mertNo=" + b.getMert_no() +
                        "&outTradeNo=" + b.getOut_trade_no() +
                        "&amount=" + b.getAmount() +
                        "&status=" + b.getStatus();
                // 加密
                param.put("sign", MD5Util.md5(reqStr));
                String result = HttpRequestUtil.post(b.getNotify_url(),param);
                // 回调次数，暂时不处理
                // 根据平台反馈结果来进行状态的修改，平台正常上分了才叫回调成功[已支付，未成功回调，需要平台正常上分才叫回调成功]
                if (result != null && !result.equals("")){
                    // 修改状态
                    if (result.equals("SUCCESS")){
                        log.info("=============================  平台已处理订单，正常回调，订单号：" + b.getOut_trade_no() + " ====================================");
                        aliOrderMapper.updateCallBackStatus(b.getOut_trade_no());
                    }
                }
            }
        }
    }
}
