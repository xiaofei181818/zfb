package com.xiaofei.pay.comxiaofeipay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xiaofei.pay.comxiaofeipay.mapper")
@EnableScheduling
public class ComXiaofeiPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComXiaofeiPayApplication.class, args);
    }

}
