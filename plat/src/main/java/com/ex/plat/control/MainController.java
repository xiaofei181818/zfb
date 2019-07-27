package com.ex.plat.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping(value = "plat")
    public String plat(String mert_no,String out_trade_no,String total_amount,String status){
        System.out.println(mert_no);
        System.out.println(out_trade_no);
        System.out.println(total_amount);
        System.out.println(status);
        return "SUCCESS";
    }
}
