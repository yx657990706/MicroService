package com.yx.basejobservice.service;

import com.yx.basecoreservice.exception.ServiceException;
import com.yx.basejobservice.model.GlGameBetSyncLog;

import java.util.Date;

/**
 * @Author Jesse
 * @Date 2019/10/19 15:27
 **/
public interface GameHistoryProxyService {
    GlGameBetSyncLog getLastSyncLog(Integer channelId, Integer type) throws ServiceException;

    void createSyncLog(Integer channelId, Integer type, Date fromTime, Date toTime, Date startTime);

    Integer doGameHistory(Integer channelId, Integer type, Date from, Date to, Date startTime) throws ServiceException;
}
