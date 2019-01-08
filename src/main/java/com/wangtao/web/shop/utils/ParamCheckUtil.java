package com.wangtao.web.shop.utils;

import com.wangtao.web.shop.common.exception.CodeException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by administer on 2017/10/24.
 * 接口参数检查工具类
 */
public class ParamCheckUtil {

    public static void check(Map map, String... paramNames) throws CodeException {
        if (map != null) {
            for (String paramName : paramNames) {
                if (!map.containsKey(paramName) || map.get(paramName) == null) {
                    throw new CodeException(ResultCode.GLOBAL_REQUEST_PARAMETER_MISSING.getCode(),
                            ResultCode.GLOBAL_REQUEST_PARAMETER_MISSING.getMsg());
                }
            }
        }else {
            throw new CodeException(ResultCode.GLOBAL_REQUEST_PARAMETER_MISSING.getCode(),
                    ResultCode.GLOBAL_REQUEST_PARAMETER_MISSING.getMsg());
        }
    }

    public static void isEmpty(Map map, String... paramNames) throws CodeException {
        if (map != null) {
        for (String paramName : paramNames) {
            if (!map.containsKey(paramName) || map.get(paramName) == null || map.get(paramName).toString().equals("")) {
                throw new CodeException(ResultCode.GLOBAL_REQUEST_PARAMETER_MISSING.getCode(),
                        ResultCode.GLOBAL_REQUEST_PARAMETER_MISSING.getMsg());
            }
        }   }else {
            throw new CodeException(ResultCode.GLOBAL_REQUEST_PARAMETER_MISSING.getCode(),
                    ResultCode.GLOBAL_REQUEST_PARAMETER_MISSING.getMsg());
        }
    }

    public static void putHeaderToParams(Map map, HttpServletRequest request,String... paramNames) throws CodeException {
        for (String paramName : paramNames) {
            map.put(paramName, request.getHeader(paramName));
        }
    }

}
