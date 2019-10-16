package com.yx.appgameservice.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GlGameUser implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 渠道ID：1AG，2EBET，3BBIN，4PT，5MG，6GG，7 贝博体育
     */
    private Integer channelId;

    /**
     * 游戏账号
     */
    private String username;

    /**
     * 游戏密码
     */
    private String password;

    /**
     * 游戏余额
     */
    private BigDecimal balance;

    /**
     * 可转出金额（可提现余额）
     */
    private BigDecimal validBalance;

    /**
     * 用户状态：0启用，1停用
     */
    private Integer status;

    /**
     * 返水是否开启：0开启，1关闭
     */
    private Integer rebateOpen;

    /**
     * 用户返水比例
     */
    private Integer rebate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;
}