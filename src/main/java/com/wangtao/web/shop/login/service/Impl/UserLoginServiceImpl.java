package com.wangtao.web.shop.login.service.Impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wangtao.web.shop.login.dao.UserLoginMapper;
import com.wangtao.web.shop.login.service.UserLoginService;
import com.wangtao.web.shop.utils.ResMsg;
import com.wangtao.web.shop.utils.httpApiDemo.IndustrySMS;
import com.wangtao.web.shop.utils.httpApiDemo.common.Config;
import com.wangtao.web.shop.utils.redis.RedisUtil;
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

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map sendMessage(Map map) {
        //1.验证code是否正确
        String mobileCode = redisUtil.get("captchaCode:" + map.get("captchaCode"));
        if (mobileCode == null || !mobileCode.equals(map.get("captchaCode"))) {
            return ResMsg.error();
        }

        //2.查看当天发送多少条
        if (userLoginMapper.sendDayCount(map) > 10) {
            return ResMsg.error("每天发送已达最上限：10条");
        }
        //发送短信
        String code = RandomUtil.randomNumbers(6);
        map.put("mobileCode",code);
        String smsContent = Config.SMS_MOUDLE.replace("{1}", code);
        String resultJson = IndustrySMS.execute(map.get("userMobile").toString(), smsContent);
        if (resultJson != null) {
            JSONObject result = JSONUtil.parseObj(resultJson);
            if ("00000".equals(result.get("respCode"))) {
                //成功发送 写入库
                userLoginMapper.insMessageLog(map);
                return ResMsg.success();
            }else {
                return ResMsg.error("短信发送失败，请重试");
            }
        }else {
            return ResMsg.error("短信发送失败，请重试");
        }
    }

    @Override
    public int checkToken(Map map) {
        return userLoginMapper.checkToken(map);
    }

    @Override
    public Map userLogin(Map map) {
        //1.判断手机号验证码
        if (userLoginMapper.checkVertifyCode(map) == 0) {
            return ResMsg.error("手机验证码错误");
        }
        //2.新老用户

        if (userLoginMapper.oldUser(map) == 0) {
            String token = IdUtil.simpleUUID();
            map.put("token",token);
            userLoginMapper.insUser(map);
        } else {
            userLoginMapper.updUser(map);
        }
        //3.获取用户信息
        Map userInfo = userLoginMapper.selUserInfo(map);
        if (userInfo == null) {
            return ResMsg.error("登录失败.请重新登录");
        } else {
            return ResMsg.success(userInfo);
        }


    }
}
