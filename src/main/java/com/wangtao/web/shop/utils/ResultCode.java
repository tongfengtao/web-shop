package com.wangtao.web.shop.utils;

/**
 * @project web_shop
 * @create by wangtao
 * @createTime 2019/1/8 21:33
 */
public enum ResultCode {
    GLOBAL_REQUEST_SUCCESS(1,"请求成功标识","请求成功"),
    GLOBAL_REQUEST_ERROR(2,"请求成功失败","请求失败"),
    GLOBAL_REQUEST_OTHER_ERROR(999,"系统繁忙请稍后再试","系统繁忙,请稍后再试"),
    GLOBAL_REQUEST_PARAMETER_MISSING(-1,"接口参数缺失","接口参数缺失");

    private int code;
    private String description;
    private String msg;

    ResultCode(int code, String description, String msg) {
        this.code = code;
        this.description = description;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDescription() {
        return description;
    }

}
