package com.xiaofei.pay.comxiaofeipay.mapper;

import com.xiaofei.pay.comxiaofeipay.bean.AliOrderBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AliOrderMapper{

    /**
     * 添加支付订单
     * @param bean
     * @return
     */
    @Insert("insert into order_info(out_trade_no,`create`,status,channel,amount,notify_url,call_back,call_no) " +
            "values(#{out_trade_no},#{create},#{status},#{channel},#{amount},#{notify_url},#{call_back},#{call_no})")
    public Long add(AliOrderBean bean);

    /**
     * 修改订单支付状态
     * @param out_trade_no
     */
    @Update("update order_info set status = 1 where out_trade_no = #{out_trade_no}")
    public void updateStatus(@Param("out_trade_no")String out_trade_no);

    /**
     * 修改回调状态
     * @param out_trade_no
     */
    @Update("update order_info set call_back = 1 where out_trade_no = #{out_trade_no}")
    public void updateCallBackStatus(@Param("out_trade_no")String out_trade_no);

    /**
     * 查询所有订单
     */
    @Select("select * from order_info")
    public List<AliOrderBean> findAll();

    /**
     * 查询已支付回调次数低于3次的订单
     */
    @Select("select * from order_info where status = 1 and call_back=0 and call_no <= 3")
    public List<AliOrderBean> findCallOrder();

    /**
     * 通过订单号查找支付状态
     * @param no
     * @return
     */
    @Select("select status from order_info where out_trade_no = #{no}")
    public int findStatusByNo(@Param("no")String no);
}
