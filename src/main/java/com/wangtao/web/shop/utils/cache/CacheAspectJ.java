package com.wangtao.web.shop.utils.cache;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class CacheAspectJ extends AspectJ {

    private Cache cache;

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Pointcut("@annotation(com.wangtao.web.shop.utils.cache.Cache)")
    public void methodCachePointcut(){}

    @Around("methodCachePointcut()")
    public Object methodCacheHold(ProceedingJoinPoint pjp) throws Throwable{
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Cache cache = (Cache) method.getAnnotation(Cache.class);
        String[] parameterNames  = ((MethodSignature) pjp.getSignature()).getParameterNames();
        Class T = method.getReturnType();
        Object result = null;
        String cacheKey = null;
        boolean setArguments = true;
        if (null != cache) {
            try {
                cacheKey = cache.cacheKey();
                setArguments = cache.setArguments();
                long expire = cache.expire();
                if (cacheKey == null || cacheKey.equals("")) {
                    String className = pjp.getTarget().getClass().getSimpleName();
                    String methodName = method.getName();
                    cacheKey = className + "." + methodName;
                }
                //如果用了表达式的则单独处理
                if(cacheKey.startsWith("#") && cacheKey.endsWith("#")){
                    Object[] arguments = pjp.getArgs();
                    cacheKey = getArgumentsValue(cacheKey,parameterNames,arguments);
                }
                if(setArguments){
                    Object[] arguments = pjp.getArgs();
                    cacheKey = getCacheKey(cacheKey, arguments);
                }
                Object value = redisUtil.get(cacheKey);
                if (value == null) {
                    result = pjp.proceed();
                    if (result != null) {
                        log.info("cached,cache not null,key:" + cacheKey);
                        if(T == Map.class || T == List.class || T == PageInfo.class){
                            if(expire==0l){
                                redisUtil.set(cacheKey, JSON.toJSONString(result));
                            }else{
                                redisUtil.set(cacheKey, JSON.toJSONString(result), expire);
                            }
                        }else{
                            if(expire==0l){
                                redisUtil.set(cacheKey, result);
                            }else{
                                redisUtil.set(cacheKey, result, expire);
                            }
                        }
                    } else {
                        log.info("cached,no cache null,key:" + cacheKey);
                    }
                } else {
                    if(T == Map.class || T == List.class || T == PageInfo.class){
                        result = JSON.parseObject(value.toString(),T);
                    }else{
                        result = value;
                    }
                    //redisUtil.expire(cacheKey,expire);//重置过期时间
                    log.info("cached,key:" + cacheKey);
                }
            } catch (Exception e) {
                log.error("$$key:" + cacheKey + ":$$" + e.getMessage(), e);
                result = pjp.proceed();
            }

        } else {
            result = pjp.proceed();
        }
        return result;
    }
}
