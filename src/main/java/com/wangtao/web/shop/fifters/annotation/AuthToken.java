package com.wangtao.web.shop.fifters.annotation;

import java.lang.annotation.*;

/**
 * 添加此注解并置为false，代表不进入拦截
 */
@Documented
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthToken {
    boolean validate() default  true;
}
