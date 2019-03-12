package com.fxcrm.utils.exception;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class MyExceptionUtils{

    public String ex(Exception e) {
        if (e instanceof feign.RetryableException) {
            return "连接超时";
        }
        return "未定义的其它错误";
    }
}
