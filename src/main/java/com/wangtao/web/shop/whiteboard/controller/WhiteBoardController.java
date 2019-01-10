package com.wangtao.web.shop.whiteboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project web_shop
 * @create by wangtao
 * @createTime 2019/1/7 22:39
 */
@RestController
public class WhiteBoardController {

    @RequestMapping(value = "user/audit")
    public  String asd(){
        System.out.println(123123);
        return "chifan";
    }
}
