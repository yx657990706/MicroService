package com.yx.basereportservice.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型
 */
public enum UserTypeEnum {

    MEMBER(0),

    PROXY(1),

    UNKNOWN(-1);

    private int value;

    // values map
    private final static Map<Integer, Enum> values;

    static {
        values = new HashMap<>();
        for (UserTypeEnum p : UserTypeEnum.values()) {
            values.put(p.value, p);
        }
    }

    UserTypeEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int value() {
        return this.value;
    }

    public static UserTypeEnum valueOf(int p) {
        UserTypeEnum platform = (UserTypeEnum) values.get(p);
        if (null == platform) {
            return UNKNOWN;
        }
        return platform;
    }
}