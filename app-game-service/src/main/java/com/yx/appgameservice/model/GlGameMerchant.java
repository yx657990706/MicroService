package com.yx.appgameservice.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GlGameMerchant implements Serializable {
	
	private static final long serialVersionUID = -7328606272309278680L;

	/**
     * 账号ID
     */
    private Integer merchantId;

    /**
     * 渠道ID
     */
    private Integer channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 游戏类型：0彩票，1真人，2体育，3老虎机，4捕鱼
     */
    private Integer gameType;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 商户域名
     */
    private String domain;

    /**
     * 0正常，1已禁用，2已删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastUpdate;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 授权令牌
     */
    private String authToken;

    /**
     * 令牌有效期
     */
    private Date validTime;

    /**
     * 请求地址
     */
    private String uri;
    /**
     * 启动地址
     */
    private String startUrl;

}