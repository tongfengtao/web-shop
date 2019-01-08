package com.wangtao.web.shop.login.service.Impl;

import com.wangtao.web.shop.login.dao.UserLoginMapper;
import com.wangtao.web.shop.login.service.UserLoginService;
import com.wangtao.web.shop.utils.ResMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @project web_shop
 * @create by wangtao
 * @createTime 2019/1/8 20:59
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public Map userLogin(Map map) {
        //1.判断手机号验证码
        if (userLoginMapper.checkVertifyCode(map)==0) {
            return ResMsg.error("手机验证码错误");
        }
        //2.新老用户
        if(userLoginMapper.oldUser(map)==0){
            userLoginMapper.insUser(map);
        }else {
            userLoginMapper.updUser(map);
        }
        //3.获取用户信息
        Map userInfo = userLoginMapper.selUserInfo(map);
        if(userInfo==null){
            return ResMsg.error("登录失败.请重新登录");
        }else {
            return ResMsg.success(userInfo);
        }


    }
}
