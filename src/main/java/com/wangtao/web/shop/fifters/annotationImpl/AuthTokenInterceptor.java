package com.wangtao.web.shop.fifters.annotationImpl;

import cn.hutool.core.util.StrUtil;
import com.wangtao.web.shop.common.exception.CodeException;
import com.wangtao.web.shop.fifters.annotation.AuthToken;
import com.wangtao.web.shop.login.service.UserLoginService;
import com.wangtao.web.shop.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenInterceptor extends HandlerInterceptorAdapter {
    private Logger log = LoggerFactory.getLogger(AuthTokenInterceptor.class);
    @Autowired
    private UserLoginService userLoginServicel;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")){
            return true;
        }
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            String token = request.getHeader("token");
            String userId = request.getHeader("userId");
            Map map=new HashMap();
            map.put("token",token);
            map.put("userId",userId);
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //返回该元素的方法上的注释，如果是这样的注释，否则返回null。
            AuthToken methodAnnotation = handlerMethod.getMethodAnnotation(AuthToken.class);
            //返回该元素的类上的注释，如果是这样的注释，否则返回null。
            AuthToken annotation = handlerMethod.getBeanType().getAnnotation(AuthToken.class);

            if ((methodAnnotation != null && methodAnnotation.validate()) || (methodAnnotation == null && annotation != null && annotation.validate())) {

                if (StrUtil.isBlank(token)||StrUtil.isBlank(userId))
                    throw new CodeException(ResultCode.GLOBAL_REQUEST_TOKEN.getCode(), ResultCode.GLOBAL_REQUEST_TOKEN.getMsg());
                //验证token
                if(userLoginServicel.checkToken(map)==0){
                    throw new CodeException(ResultCode.GLOBAL_REQUEST_TOKEN.getCode(), ResultCode.GLOBAL_REQUEST_TOKEN.getMsg());
                }
            }
        }


        return super.preHandle(request, response, handler);
    }
}
