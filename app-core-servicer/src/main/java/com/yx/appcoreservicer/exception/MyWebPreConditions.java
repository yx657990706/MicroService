package com.yx.appcoreservicer.exception;


import com.yx.appcoreservicer.enums.EnumBusinessErrorCode;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2019/1/9
 * @time 2:58 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class MyWebPreConditions {

    public static void assertTrue(final boolean expression, final String code, final String errorMessage) {
        if (!expression) {
            throw new MyException(code, errorMessage);
        }
    }

    public static void assertTrue(final boolean expression, EnumBusinessErrorCode enumBusinessErrorCode) {
        if (!expression) {
            throw new MyException(enumBusinessErrorCode.getErrorCode(), enumBusinessErrorCode.getErrorMsg());
        }
    }

    public static <T> T checkNotNull(final T reference, String code, final String errorMessage) {
        if (reference == null) {
            throw new MyException(code, errorMessage);
        }
        return reference;
    }

    public static <T> T checkNotNull(final T reference, EnumBusinessErrorCode enumBusinessErrorCode) {
        if (reference == null) {
            throw new MyException(enumBusinessErrorCode.getErrorCode(), enumBusinessErrorCode.getErrorMsg());
        }
        return reference;
    }
}
