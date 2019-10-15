package com.yx.basereportservice.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类型
 */
public enum MsgEnum {

    /**
     * 充值
     */
    Recharge(1000, "充值"),

    /**
     * 提现
     */
    Withdraw(1001, "提现"),

    /**
     * 提现-父单(针对拆单的父单)
     */
    WithdrawParentOrder(1025, "大额提现拆单"),

    /**
     * 提现退回
     */
    WithdrawReturn(1007, "提现退回"),

    /**
     * 游戏转账
     */
    Transfer(1002, "转账"),

    /**
     * 投注
     */
    Betting(1003, "投注"),

    /**
     * 用户注册
     */
    Register(1004, "用户注册"),

    /**
     * 用户登录
     */
    Login(1005, "用户登录"),

    /**
     * 在线人数
     */
    OnlineCount(1006, "在线人数"),

    /**
     * 红利（加币）
     */
    Bonus(1009, "红利"),

    /**
     * 返水
     */
    Rebate(1010, "返水"),

    /**
     * 减币
     */
    SubCoin(1011, "减币"),

    /**
     * 佣金
     */
    Commission(1012, "佣金"),

    /**
     * 上级转入
     */
    CreditTransfer(1013, "上级转入"),

    /**
     * 游戏余额变动
     */
    GameUserBalance(1014, "游戏余额变动"),

    /**
     * 上分
     */
    UpAmount(1015, "上分"),

    /**
     * 下分
     */
    SubAmount(1016, "下分"),

    /**
     * 上分清算-人工操作清算
     */
    UpAmountBalance(1017, "上分清算-人工操作清算"),

    /**
     * 加币
     */
    AddCoin(1018, "加币");



    private int value;
    private String desc;

    MsgEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    // msg map
    private final static Map<Integer, MsgEnum> msgMap;

    static {
        msgMap = new HashMap<>();
        for (MsgEnum m : MsgEnum.values()) {
            msgMap.put(m.value, m);
        }
    }

    // int to enum
    public static MsgEnum valueOf(int e) {
        return msgMap.get(e);
    }

    @JsonValue
    public int value() {
        return this.value;
    }

    public String desc() {
        return this.desc;
    }
}