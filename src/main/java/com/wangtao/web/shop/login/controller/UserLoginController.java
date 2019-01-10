package com.wangtao.web.shop.login.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.wangtao.web.shop.common.exception.CodeException;
import com.wangtao.web.shop.fifters.annotation.AuthToken;
import com.wangtao.web.shop.login.service.UserLoginService;
import com.wangtao.web.shop.utils.ParamCheckUtil;
import com.wangtao.web.shop.utils.ResMsg;
import com.wangtao.web.shop.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private RedisUtil redisUtil;
    @RequestMapping("/login")
    public Map userLogin(@RequestBody Map map) throws CodeException{
        ParamCheckUtil.check(map,"userMobile","mobileCode");
        return userLoginService.userLogin(map);
    }

    @RequestMapping("/send/message")
    @AuthToken
    public  Map sendMessage(@RequestBody Map map)throws CodeException{
        ParamCheckUtil.check(map,"userMobile","captchaCode");

        return userLoginService.sendMessage(map);
    }
    @RequestMapping("/captcha/code")
    public void loginCaptcha(@RequestBody Map map, HttpServletResponse response) throws Exception{
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        //ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
        //图形验证码写出，可以写出到文件，也可以写出到流
        //captcha.write("d:/shear.png");
        String code = captcha.getCode();
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        outputStream.flush();
        outputStream.close();
        //验证图形验证码的有效性，返回boolean值
        if(captcha.verify(code)){
            redisUtil.set("captchaCode:"+code,code);
        }
    }

}
