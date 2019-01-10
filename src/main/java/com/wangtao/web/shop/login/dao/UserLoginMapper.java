package com.wangtao.web.shop.login.dao;

import java.util.Map;

/**
 * Created by Administrator on 2019/1/8.
 */
public interface UserLoginMapper {
    int checkVertifyCode(Map map);

    int oldUser(Map map);

    Map selUserInfo(Map map);

    void updUser(Map map);

    void insUser(Map map);

    int sendDayCount(Map map);

    void insMessageLog(Map map);

    int checkToken(Map map);
}
