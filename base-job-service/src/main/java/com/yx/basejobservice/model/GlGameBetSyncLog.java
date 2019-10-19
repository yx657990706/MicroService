package com.yx.basejobservice.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GlGameBetSyncLog  implements Serializable {
	
	private static final long serialVersionUID = 3874278146255357714L;

	/**
     * 自增主键
     */
    private Integer logId;

    /**
     * 渠道ID
     */
    private Integer channelId;

    /**
     * 账号ID
     */
    private Integer merchantId;

    /**
     * 记录起始时间
     */
    private Date fromTime;

    /**
     * 记录截止时间
     */
    private Date toTime;

    /**
     * 同步开始时间
     */
    private Date startTime;

    /**
     * 同步结束时间
     */
    private Date endTime;

    /**
     * 获取自增主键
     *
     * @return log_id - 自增主键
     */
    public Integer getLogId() {
        return logId;
    }

}