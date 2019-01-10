package com.wangtao.web.shop.fifters;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.wangtao.web.shop.common.exception.CodeException;
import com.wangtao.web.shop.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        FastJsonJsonView fastJsonJsonView = new FastJsonJsonView();
        Map error = new HashMap<>();
        StackTraceElement[] stackTrace = e.getStackTrace();
        for(StackTraceElement element : stackTrace) {
            if(element.getClassName().startsWith("com.wangtao.web.shop")) {
                Logger logger = LoggerFactory.getLogger(element.getClassName());
                logger.info("EXCEPTION_LOG_BEGIN--------------------------------------------------------------");
                String requestInfo = "";
                if(!e.getClass().isAssignableFrom(CodeException.class) && !e.getClass().isAssignableFrom(UndeclaredThrowableException.class)) {
                    requestInfo = printRequestHeaderParams(httpServletRequest);
                    try{
                        ResultCode resultCode = ResultCode.valueOf(element.getClassName().toUpperCase() +"_"+ element.getMethodName().toUpperCase());
                        error.put("code", resultCode.getCode());
                        error.put("msg", resultCode.getMsg()+"("+System.currentTimeMillis()+")");
                    }catch (Exception e1) {
                        error.put("code",ResultCode.GLOBAL_REQUEST_OTHER_ERROR.getCode());
                        error.put("msg","系统繁忙请稍后再试");
                        //测试环境-暂时输出原本异常信息
                        //error.put("msg", ((CodeException) ex).getMsg());
                    }

                    String code = String.valueOf(error.get("code"));
                    if(!code.equals("-130") && !code.equals("-120") && !code.equals("-150")){
                        logger.error("URL:"+(httpServletRequest.getRequestURI()==null?"":httpServletRequest.getRequestURI())+
                                ",CODE:"+error.get("code")+
                                ",CLASS:"+element.getClassName()+ "." + element.getMethodName() + " in " + element.getLineNumber() + " line"+
                                ",MSG:"+getExceptionAllinformation(e)+",REQUESTINFO:"+requestInfo);
                    }
                }else if(e.getClass().isAssignableFrom(UndeclaredThrowableException.class)){
                    requestInfo = printRequestHeaderParams(httpServletRequest);
                    UndeclaredThrowableException undeclaredThrowableException = (UndeclaredThrowableException) e;
                    if(undeclaredThrowableException.getUndeclaredThrowable() instanceof CodeException){
                        CodeException e1 = (CodeException) undeclaredThrowableException.getUndeclaredThrowable();
                        error.put("code",e1.getCode());
                        error.put("msg",e1.getMsg());
                        if(e1.getData()!=null) error.put("data",e1.getData());
                    }else{
                        error.put("code",ResultCode.GLOBAL_REQUEST_OTHER_ERROR.getCode());
                        error.put("msg","系统繁忙请稍后再试");
                        //测试环境-暂时输出原本异常信息
                        //error.put("msg", ((CodeException) ex).getMsg());
                    }

                    String code = String.valueOf(error.get("code"));
                    if(!code.equals("-130") && !code.equals("-120") && !code.equals("-150")) {
                        logger.error("URL:" + (httpServletRequest.getRequestURI() == null ? "" : httpServletRequest.getRequestURI()) +
                                ",CODE:" + error.get("code") +
                                ",CLASS:" + element.getClassName() + "." + element.getMethodName() + " in " + element.getLineNumber() + " line" +
                                ",MSG:" + getExceptionAllinformation(e)+",REQUESTINFO:"+requestInfo);
                    }
                }else{
                    requestInfo = printRequestHeaderParams(httpServletRequest);
                    CodeException e1 = (CodeException) e;
                    error.put("code",e1.getCode());
                    error.put("msg",e1.getMsg());
                    if(e1.getData()!=null) error.put("data",e1.getData());

                    String code = String.valueOf(error.get("code"));
                    if(!code.equals("-130") && !code.equals("-120") && !code.equals("-150")) {
                        logger.error("URL:" + (httpServletRequest.getRequestURI() == null ? "" : httpServletRequest.getRequestURI()) +
                                ",CODE:" + error.get("code") +
                                ",CLASS:" + element.getClassName() + "." + element.getMethodName() + " in " + element.getLineNumber() + " line" +
                                ",MSG:" + e1.getMsg() + ";" + getExceptionAllinformation(e)+",REQUESTINFO:"+requestInfo);
                    }
                }
                //e.printStackTrace();
                logger.info("EXCEPTION_LOG_END--------------------------------------------------------------");
                break ;
            }
        }
        fastJsonJsonView.setAttributesMap(error);
        modelAndView.setView(fastJsonJsonView);
        return modelAndView;
    }

    public String printRequestHeaderParams(HttpServletRequest request){
        StringBuffer requestInfo = new StringBuffer();
        try {
            if(request!=null){
                logger.info("Header--------------------------------------------------------------");
                requestInfo.append("Header--------------------------------------------------------------");
                requestInfo.append("\r\n");
                Enumeration headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String key = (String) headerNames.nextElement();
                    String value = request.getHeader(key);
                    logger.info(key+": "+value);
                    requestInfo.append(key+": "+value);
                    requestInfo.append("\r\n");
                }
                logger.info("Parameter--------------------------------------------------------------");
                requestInfo.append("Parameter--------------------------------------------------------------");
                requestInfo.append("\r\n");
                Enumeration enu=request.getParameterNames();
                while(enu.hasMoreElements()){
                    String paraName=(String)enu.nextElement();
                    logger.info(paraName+": "+request.getParameter(paraName));
                    requestInfo.append(paraName+": "+request.getParameter(paraName));
                    requestInfo.append("\r\n");
                }
                logger.info("URL--------------------------------------------------------------");
                requestInfo.append("URL--------------------------------------------------------------");
                requestInfo.append("\r\n");
                logger.info(request.getRequestURI());
                requestInfo.append(request.getRequestURI());
                requestInfo.append("\r\n");
            }
        }catch (Exception e){
            logger.info("请求参数获取错误");
        }
        return requestInfo.toString();
    }

    public static String getExceptionAllinformation(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e) {
        }
        return ret;
    }
}
