package com.xiaofei.pay.comxiaofeipay.mapper;

import com.xiaofei.pay.comxiaofeipay.bean.ShopBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ShopMapper {

    @Select("select * from shop_info where no = #{no}")
    public ShopBean findByNo(@Param("no")String no);
}
