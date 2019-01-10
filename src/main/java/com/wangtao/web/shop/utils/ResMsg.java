package com.wangtao.web.shop.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @project web_shop
 * @create by wangtao
 * @createTime 2019/1/8 21:59
 */
public class ResMsg {

    public static Map<String,Object> success(){
        Map<String,Object> map = new HashMap();
        map.put("code", ResultCode.GLOBAL_REQUEST_SUCCESS.getCode());
        map.put("msg",ResultCode.GLOBAL_REQUEST_SUCCESS.getMsg());
        return map;
    }
    public static Map<String,Object> success(String msg){
        Map<String,Object> map = new HashMap();
        map.put("code",ResultCode.GLOBAL_REQUEST_SUCCESS.getCode());
        map.put("msg",msg);
        return map;
    }
    public static Map<String,Object> success(Object object){
        Map<String,Object> map = new HashMap();
        map.put("code",ResultCode.GLOBAL_REQUEST_SUCCESS.getCode());
        map.put("msg",ResultCode.GLOBAL_REQUEST_SUCCESS.getMsg());
        map.put("data",object);
        return map;
    }
    public static Map<String,Object> success(String msg,Object object){
        Map<String,Object> map = new HashMap();
        map.put("code",ResultCode.GLOBAL_REQUEST_SUCCESS.getCode());
        map.put("msg",msg);
        map.put("data",object);
        return map;
    }

    public static Map<String,Object> error(){
        Map<String,Object> map = new HashMap();
        map.put("code",ResultCode.GLOBAL_REQUEST_ERROR.getCode());
        map.put("msg",ResultCode.GLOBAL_REQUEST_ERROR.getMsg());
        return map;
    }
    public static Map<String,Object> error(String msg){
        Map<String,Object> map = new HashMap();
        map.put("code",ResultCode.GLOBAL_REQUEST_ERROR.getCode());
        map.put("msg",msg);
        return map;
    }
    public static Map<String,Object> error(Object object){
        Map<String,Object> map = new HashMap();
        map.put("code",ResultCode.GLOBAL_REQUEST_ERROR.getCode());
        map.put("msg",ResultCode.GLOBAL_REQUEST_ERROR.getMsg());
        map.put("data",object);
        return map;
    }
    public static Map<String,Object> error(String msg,Object object){
        Map<String,Object> map = new HashMap();
        map.put("code",ResultCode.GLOBAL_REQUEST_ERROR.getCode());
        map.put("msg",msg);
        map.put("data",object);
        return map;
    }
    public static Map<String,Object> other(Integer code,String msg){
        Map<String,Object> map = new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }


    public static Map<String,Object> other(ResultCode resultCode,Object obj){
        Map<String,Object> map = new HashMap();
        map.put("code",resultCode.getCode());
        map.put("msg",resultCode.getMsg());
        map.put("data",obj);
        return map;
    }

    public static Map<String,Object> other(ResultCode resultCode){
        Map<String,Object> map = new HashMap();
        map.put("code",resultCode.getCode());
        map.put("msg",resultCode.getMsg());
        return map;
    }

    public static Map<String,Object> other(Integer code,String msg,Object object){
        Map<String,Object> map = new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",object);
        return map;
    }
    public static Map<String,Object> error(int code,String msg){
        Map<String,Object> map = new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }
}
