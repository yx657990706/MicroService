package com.yx.basejobservice.service.impl;

import com.yx.appgameservice.enums.GameChannelCode;
import com.yx.appgameservice.model.GlGameMerchant;
import com.yx.basecoreservice.exception.ServiceException;
import com.yx.basecoreservice.exception.enums.EnumExceptionResult;
import com.yx.basejobservice.model.GlGameBetSyncLog;
import com.yx.basejobservice.service.GameHistoryProxyService;
import com.yx.basejobservice.service.GameHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class GameHistoryServiceImpl implements GameHistoryProxyService {

    @Autowired
    private Map<String, GameHistoryService> gameHistoryHandlerMap;

//    @Resource
//    private GlGameBetSyncLogMapper glGameBetSyncLogMapper;
//    @Resource
//    private GlGameMerchantMapper glGameMerchantMapper;

    @Override
    public GlGameBetSyncLog getLastSyncLog(Integer channelId, Integer type) throws ServiceException {
        //TODO  查询最新一条同步日志
//        return glGameBetSyncLogMapper.findByChannelId(channelId, type);
        return null;
    }

    @Override
    public void createSyncLog(Integer channelId, Integer type, Date fromTime, Date toTime, Date startTime) {
//        GlGameBetSyncLog gameLog = new GlGameBetSyncLog();
//        gameLog.setChannelId(channelId);
//        gameLog.setEndTime(new Date());
//        gameLog.setFromTime(fromTime);
//        gameLog.setStartTime(startTime);
//        gameLog.setToTime(toTime);
//        gameLog.setMerchantId(type);
//        glGameBetSyncLogMapper.insertSelective(gameLog);
        //TODO  插入同步日志
    }

    @Override
    public Integer doGameHistory(Integer channelId, Integer type, Date from, Date to, Date startTime) throws ServiceException {
        try {
            String handlerId = channelId.toString();
            if (channelId == GameChannelCode.CHANNEL_AG) {
                if(type < 100){
                    handlerId = handlerId + "0" + type % 3;
                }else{
                    handlerId = handlerId + type;
                }
            }
            GameHistoryService handler = gameHistoryHandlerMap.get(handlerId);
            if (handler == null) {
                log.warn("no game history handler for handlerId: {}, break history sync.", handlerId);
                return 0;
            }
            //TODO 获取商户信息
            GlGameMerchant merchant = null;//glGameMerchantMapper.getMerchantByChannelId(channelId);
            if (merchant == null) {
                log.warn("not valid merchant for channelId: {}, break history sync.", channelId);
                return 0;
            }
            Integer count = handler.doGameHistory(merchant, type, from, to, startTime);
//            log.info("doGameHistory gamePlayformId:{} ,count: {}", merchant.getChannelId() * merchant.getGameType(), count);
            return count;
        } catch (Exception e) {
            log.error("history sync exception for channelId: " + channelId + ", type: " + type, e);
            throw new ServiceException(EnumExceptionResult.ERROR_UNKOWN);
        }
    }
}
