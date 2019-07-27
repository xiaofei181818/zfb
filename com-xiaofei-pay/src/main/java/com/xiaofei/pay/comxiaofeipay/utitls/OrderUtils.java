package com.xiaofei.pay.comxiaofeipay.utitls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *  订单工具类
 */
public class OrderUtils {

    public static String getOrderNo(){
        return new Date().getTime()+ UUID.randomUUID().toString();
    }

    public static String getTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }
}
