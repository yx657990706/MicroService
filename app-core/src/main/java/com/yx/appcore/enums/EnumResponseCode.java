package com.yx.appcore.enums;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/19
 * @time 2:13 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public enum EnumResponseCode {
    /** 正确 **/
    SUCCESS_CODE(200),
    /** 参数错误 **/
    PARAM_ERROR_CODE(400),
    /** 限制调用 **/
    LIMIT_ERROR_CODE(401),
    /** token 过期 **/
    TOKEN_TIMEOUT_CODE(402),
    /** 禁止访问 **/
    NO_AUTH_CODE(403),
    /** 资源没找到 **/
    NOT_FOUND(404),
    /** 服务器错误 **/
    SERVER_ERROR_CODE(500),
    /** 服务降级中 **/
    DOWNGRADE(406);

    private int code;

    public int getCode() {
        return code;
    }
    private EnumResponseCode(int code) {
        this.code = code;
    }
}
