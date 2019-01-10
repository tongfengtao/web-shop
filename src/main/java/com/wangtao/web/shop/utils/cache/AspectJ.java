package com.wangtao.web.shop.utils.cache;


import cn.hutool.core.date.DateUtil;

import com.wangtao.web.shop.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AspectJ {
    @Autowired
    public RedisUtil redisUtil;

    public static String getCacheKey(String cacheKey,
                               Object[] arguments) {
        StringBuffer sb = new StringBuffer(cacheKey);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i] instanceof Date) {
                    sb.append(".").append(format((Date) arguments[i]));
                } else if(arguments[i] instanceof Map){
                    SortedMap sortedMap = new TreeMap((Map) arguments[i]);
                    sb.append(".").append(sortedMap);
                }else {
                    sb.append(".").append(arguments[i]);
                }
            }
        }
        return sb.toString();
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        String s = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        if (s == null) {
            return DateUtil.format(date, "yyyy-MM-dd");
        }
        return s;
    }

    public static String getArgumentsValue(String cacheKey,String[] parameterNames,Object[] arguments){
        StringBuffer sb = new StringBuffer();
        List pList = Arrays.asList(parameterNames);
        if ((parameterNames != null) && (parameterNames.length != 0)) {
            int x ;
            String[] keys = cacheKey.substring(1,cacheKey.length()-1).split("&");
            for(int i=0;i<keys.length;i++){
                if(pList.contains(keys[i].split("\\.")[0])){
                    x = pList.indexOf(keys[i].split("\\.")[0]);
                    if (arguments[x] instanceof Date) {
                        sb.append(format((Date) arguments[x]));
                    } else if(arguments[x] instanceof Map){
                        SortedMap sortedMap = new TreeMap((Map) arguments[x]);
                        if(keys[i].split("\\.").length>1){
                            sb.append(String.valueOf(sortedMap.get(keys[i].split("\\.")[1])));
                        }else{
                            sb.append(String.valueOf(sortedMap));
                        }

                    }else {
                        sb.append(arguments[x]);
                    }
                }else{
                    sb.append(keys[i]);
                }
            }
        }
        return sb.toString();
    }

    public static int StringtoInt(String val){
        try {
            return Integer.parseInt(val);
        }catch (Exception e){
            return 0;
        }
    }
}
