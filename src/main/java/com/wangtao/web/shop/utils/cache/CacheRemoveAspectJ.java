package com.wangtao.web.shop.utils.cache;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class CacheRemoveAspectJ extends AspectJ {

    private CacheRemove cacheRemove;

    /**
     * 设置缓存名
     */
    public void setCache(CacheRemove cacheRemove) {
        this.cacheRemove = cacheRemove;
    }

    @Pointcut("@annotation(com.wangtao.web.shop.utils.cache.CacheRemove)")
    public void methodCacheRemovePointcut(){}

    @Around("methodCacheRemovePointcut()")
    public Object methodCacheRemovePointcut(ProceedingJoinPoint pjp) throws Throwable{
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        CacheRemove cacheRemove = (CacheRemove) method.getAnnotation(CacheRemove.class);
        String[] parameterNames  = ((MethodSignature) pjp.getSignature()).getParameterNames();
        Object result = null;
        String cacheKey = null;
        String pattern = null;
        if (null != cacheRemove ) {
            try {
                cacheKey = cacheRemove.cacheKey();
                pattern = cacheRemove.pattern();
                //如果用了表达式的则单独处理
                if(cacheKey.startsWith("#") && cacheKey.endsWith("#")){
                    Object[] arguments = pjp.getArgs();
                    cacheKey = getArgumentsValue(cacheKey,parameterNames,arguments);
                }
                if (!StrUtil.isBlank(cacheKey)) {
                    redisUtil.remove(cacheKey);
                    log.info("cached,cache remove,key:" + cacheKey);
                }
                //如果用了表达式的则单独处理
                if(pattern.startsWith("#") && pattern.endsWith("#")){
                    Object[] arguments = pjp.getArgs();
                    pattern = getArgumentsValue(pattern,parameterNames,arguments);
                }
                if (!StrUtil.isBlank(pattern)) {
                    redisUtil.removePattern(pattern);
                    log.info("cached,cache remove pattern,key:" + pattern);
                }
                result = pjp.proceed();
            } catch (Exception e) {
                log.error("$$remove_key:" + cacheKey + ":$$remove_pattern:"+ pattern + e.getMessage()+"*", e);
                result = pjp.proceed();
            }

        } else {
            result = pjp.proceed();
        }
        return result;
    }
}
