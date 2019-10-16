package com.yx.appgameservice.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GlGameTransfer implements Serializable {

    private static final long serialVersionUID = 1948628823963089437L;

    /**
     * 订单号
     */
    private String tradeId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户类型：0玩家，1代理
     */
    private Integer userType;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户游戏账号
     */
    private String gameUsername;

    /**
     * 渠道ID：0中心钱包，1AG，2EBET，3BBIN，4PT，5MG，6GG，7 贝博体育
     */
    private Integer fromChannelId;

    /**
     * 渠道ID：0中心钱包，1AG，2EBET，3BBIN，4PT，5MG，6GG，7 贝博体育
     */
    private Integer toChannelId;

    /**
     * 转账金额
     */
    private BigDecimal amount;

    /**
     * 过后余额
     */
    private BigDecimal balance;

    /**
     * 外部单号
     */
    private String outerId;

    /**
     * 客户端类型：0PC，1H5，2安卓，3IOS，4PAD
     */
    private Integer clientType;

    /**
     * 转账状态：0转账处理中，1转账成功，2转账失败
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastUpdate;

}