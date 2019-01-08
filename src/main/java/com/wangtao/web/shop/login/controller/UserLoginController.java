package com.wangtao.web.shop.login.controller;

import cn.hutool.core.map.MapUtil;
import com.wangtao.web.shop.common.exception.CodeException;
import com.wangtao.web.shop.login.service.UserLoginService;
import com.wangtao.web.shop.utils.ParamCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @project web_shop
 * @create by wangtao
 * @createTime 2019/1/8 20:57
 */
@RestController
@RequestMapping(value = "/user",method = RequestMethod.POST)
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping("/login")
    public Map userLogin(@RequestBody Map map) throws CodeException{
        ParamCheckUtil.check(map,"userMobile","vertifyCode");

        return userLoginService.userLogin(map);

    }

}
