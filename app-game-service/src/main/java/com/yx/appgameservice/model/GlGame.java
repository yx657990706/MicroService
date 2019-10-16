package com.yx.appgameservice.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class GlGame implements Serializable {
	
	private static final long serialVersionUID = 5199692580550320944L;

	/**
     * 游戏ID
     */
    private Integer gameId;

    /**
     * 游戏类型：0彩票，1真人，2体育，3老虎机，4捕鱼，5电竞
     */
    private Integer gameType;

    /**
     * 游戏子类型：0其他，1电动老虎机，2经典老虎机，3刮刮乐，4棋牌游戏，5街机游戏
     */
    private Integer subType;

    /**
     * 渠道ID
     */
    private Integer channelId;

    /**
     * 游戏名称
     */
    private String gameName;

    /**
     * 游戏LOGO
     */
    private String logo;

    /**
     * 游戏编码
     */
    private String gameCode;

    /**
     * 是否热门：0否，1是
     */
    private Integer hot;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 最后修改人
     */
    private String operator;

    /**
     * 最后修改时间
     */
    private Date lastUpdate;

    /**
     * 0正常，1已关闭，2已删除
     */
    private Integer status;

    /**
     * 厂商游戏名称
     */
    private String merGameName;

    /**
     * 奖池游戏 0无，1有
     */
    private Integer jackpot;

    private Integer lineNum;
}