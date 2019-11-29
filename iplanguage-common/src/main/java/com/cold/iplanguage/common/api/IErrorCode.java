package com.cold.iplanguage.common.api;

/**
 * 封装API的错误码
 * Created by ohj on 2019/11/15.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
