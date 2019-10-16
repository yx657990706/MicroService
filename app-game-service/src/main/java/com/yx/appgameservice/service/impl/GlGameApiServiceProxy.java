package com.yx.appgameservice.service.impl;

import com.google.common.collect.ImmutableList;
import com.yx.appgameservice.enums.GameChannelCode;
import com.yx.appgameservice.model.*;
import com.yx.appgameservice.service.GlGameApiService;
import com.yx.appgameservice.utils.NumStringUtils;
import com.yx.basecoreservice.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class GlGameApiServiceProxy implements GlGameApiService {

//    @Resource(name = "glGameApiServiceImpl")
//    private GlGameApiService gameApiService;
//
//    @Resource(name = "glEbetGameApiServiceImpl")
//    private GlGameApiService ebetGameApiService;
//
//    @Resource(name = "glXJGameApiService")
//    private GlGameApiService glXJGameApiService;


    List<Integer> imGameList = ImmutableList.of(GameChannelCode.CHANNEL_IM_PP,GameChannelCode.CHANNEL_IM_ESPORT,GameChannelCode.CHANNEL_IM_SW);

    @Override
    public String generatorUserName(Integer userId, Integer tryCount) {
        return NumStringUtils.numToLowerString(userId, 12 - tryCount);
    }


    @Override
    public BigDecimal doGameUserBlanceUpdate(Integer userId, Integer channelId, GlGameMerchant merchant) throws ServiceException {

        GlGameApiService targetService = getTargetService(channelId, userId);

//        if (targetService != gameApiService) {
//            merchant = gameMerchantService.getValidMerchantStrict(channelId);
//        } else {
//            merchant = gameMerchantService.getValidMerchantStrict(0);
//        }
        return getTargetService(merchant.getChannelId(), userId).doGameUserBlanceUpdate(userId, channelId, merchant);
    }

    @Override
    public GlGameUser doGameUserCreate(Integer userId, Integer channelId, GlGameMerchant merchant, String Ip) throws ServiceException {
        GlGameApiService targetService = getTargetService(channelId, userId);

//        if (targetService != gameApiService) {
//            merchant = gameMerchantService.getValidMerchantStrict(channelId);
//        } else {
//            merchant = gameMerchantService.getValidMerchantStrict(0);
//        }
        return targetService.doGameUserCreate(userId, channelId, merchant, Ip);
    }


    @Override
    public int doGameTransfer(GlUser user, GlGameTransfer transfer, GlGameMerchant merchant) throws ServiceException {
        Integer channelId = transfer.getFromChannelId() == 0 ? transfer.getToChannelId() : transfer.getFromChannelId();

        GlGameApiService targetService = getTargetService(channelId, user.getId());

//        if (targetService != gameApiService) {
//            merchant = gameMerchantService.getValidMerchantStrict(channelId);
//        } else {
//            merchant = gameMerchantService.getValidMerchantStrict(0);
//        }
        return targetService.doGameTransfer(user, transfer, merchant);
    }

    @Override
    public String doGameUrlCheck(GlGameUser user, GlGame game, GlGameMerchant merchant, Integer type, Integer device, String ip, String domain, Integer appType) throws ServiceException {

        GlGameApiService targetService = getTargetService(game.getChannelId(), user.getUserId());

//        if (targetService != gameApiService) {
//            merchant = gameMerchantService.getValidMerchantStrict(game.getChannelId());
//        } else {
//            merchant = gameMerchantService.getValidMerchantStrict(0);
//        }

        return targetService.doGameUrlCheck(user, game, merchant, type, device, ip, domain, appType);
    }


    private GlGameApiService getTargetServiceWithHistory(Integer channelId) throws ServiceException {
        return getTargetService(channelId);
    }

    private GlGameApiService getTargetService(Integer channelId, Integer userId) throws ServiceException {
        return getTargetService(channelId);
    }

    private GlGameApiService getTargetService(Integer channelId) throws ServiceException {

//        if (channelId == 1) {
//            return agGameApiService;
//        } else if (channelId == 2) {
//            return ebetGameApiService;
//        }  else if(channelId == GameChannelCode.CHANNEL_188_LIVE){
//            return glXJLiveGameApiService;
//        } else if(channelId == GameChannelCode.CHANNEL_GAME_MW){
//            return glMwGameApiService;
//        }
        return null;

    }

}
