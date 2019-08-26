package com.yx.appcore.exception.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum EnumExceptionResult {

    /**
     * 正常响应
     */
    ERROR_OK("0000", "成功"),
    /**
     * 系统异常
     */
    ERROR_UNKOWN("-1", "系统异常"),
    /**
     * 自定义错误1001
     */
    ERROR_LOW("1001", "ico资金太少,没法飞...."),
    /**
     * //自定义错误1002
     */
    ERROR_HIGHT("1002", "募集资金过多,涉嫌非法集资"),
    /**
     * Token过期
     */
    ERROR_TOKEN_TIME_OUT("1003","Token过期"),
    /**
     * Token为空
     */
    ERROR_TOKEN_NULL("1004","Token为空"),
    /**
     * Token非法
     */
    ERROR_TOKEN_ILLEGAL("1005","Token非法"),
    /**
     * ES连接失败
     */
    ERROR_ES_CONNECT("1006","ES连接失败");

    private String code;
    private String msg;

    private EnumExceptionResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
