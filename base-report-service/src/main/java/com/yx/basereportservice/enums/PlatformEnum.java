package com.yx.basereportservice.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * 平台类型
 */
public enum PlatformEnum {

    PC(0),

    H5(1),

    Android(2),

    IOS(3),

    PAD(4),

    UNKNOWN(-1);

    private int value;

    // values map
    private final static Map<Integer, Enum> values;

    static {
        values = new HashMap<>();
        for (PlatformEnum p : PlatformEnum.values()) {
            values.put(p.value, p);
        }
    }

    PlatformEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int value() {
        return this.value;
    }

    // int to enum
    public static PlatformEnum valueOf(int p) {
        PlatformEnum platform = (PlatformEnum) values.get(p);
        if (null == platform) {
            return UNKNOWN;
        }
        return platform;
    }
}