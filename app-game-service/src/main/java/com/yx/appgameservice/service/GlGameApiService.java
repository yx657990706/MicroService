package com.yx.appgameservice.service;

import com.yx.appgameservice.model.*;
import com.yx.basecoreservice.exception.ServiceException;

import java.math.BigDecimal;


public interface GlGameApiService {

    /**
     * 查询用户游戏余额
     *
     * @param userId
     * @param channelId
     * @param merchant
     * @return
     * @throws ServiceException
     */
    BigDecimal doGameUserBlanceUpdate(Integer userId, Integer channelId, GlGameMerchant merchant) throws ServiceException;

    /**
     * 创建用户游戏账户
     *
     * @param userId
     * @param channelId
     * @param merchant
     * @return
     * @throws ServiceException
     */
    GlGameUser doGameUserCreate(Integer userId, Integer channelId, GlGameMerchant merchant, String ip) throws ServiceException;

    /**
     * 获取游戏链接
     *
     * @param user
     * @param game
     * @param merchant
     * @param type
     * @param device
     * @param ip
     * @return
     * @throws ServiceException
     */
    String doGameUrlCheck(GlGameUser user, GlGame game, GlGameMerchant merchant, Integer type, Integer device, String ip, String domain, Integer appType) throws ServiceException;

    /**
     * 生成第三方用户名
     * @param userId
     * @param tryCount
     * @return
     */
    String generatorUserName(Integer userId, Integer tryCount);

    /**
     *  游戏转账
     * @param user
     * @param transfer
     * @param merchant
     * @return
     * @throws ServiceException
     */
    int doGameTransfer(GlUser user, GlGameTransfer transfer, GlGameMerchant merchant) throws ServiceException;


}
