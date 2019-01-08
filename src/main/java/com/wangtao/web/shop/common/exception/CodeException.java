package com.wangtao.web.shop.common.exception;

/**
 * @project web_shop
 * @create by wangtao
 * @createTime 2019/1/7 22:18
 */
public class CodeException extends  Exception{

    private Integer code;
    private String msg;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public CodeException(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CodeException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeException() {
    }
}
