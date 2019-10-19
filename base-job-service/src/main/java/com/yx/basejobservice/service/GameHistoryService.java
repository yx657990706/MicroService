package com.yx.basejobservice.service;

import com.yx.appgameservice.model.GlGameMerchant;
import com.yx.basecoreservice.exception.ServiceException;

import java.util.Date;

public interface GameHistoryService {

    /**
     * 执行游戏数据同步
     *
     * @param merchant
     * @param type
     * @param from
     * @param to
     * @param start
     */
    Integer doGameHistory(GlGameMerchant merchant, Integer type, Date from, Date to, Date start) throws ServiceException;
}
