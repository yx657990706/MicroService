package com.yx.basejobservice.service.impl;

import com.yx.basecoreservice.exception.ServiceException;
import com.yx.basejobservice.service.GameTransferProxyService;

/**
 * @Author Jesse
 * @Date 2019/10/20 14:56
 **/
public class GameTransferProxyServiceImpl implements GameTransferProxyService {
    /**
     * 转账状态修复
     *
     * @throws ServiceException
     */
    @Override
    public void doGameTransferFix() throws ServiceException {
//        try {
//            GlGameTransferException transfer = glGameTransferExceptionMapper.findOneToFix(org.apache.commons.lang3.time.DateUtils.addSeconds(new Date(), -5));
//            if (transfer == null) {
//                return;
//            }
//            log.info("fixing transfer: {}", transfer.getTradeId());
//            Integer gameChannel = transfer.getFromChannelId() == 0 ? transfer.getToChannelId() : transfer.getFromChannelId();
//            log.info("transfer gameChannel==>{}", gameChannel);
//            GameTransferHandler transferHandler = gameTransferHandlerMap.get("transferHandler" + gameChannel);
//            if (transferHandler == null) {
//                log.warn("no matched game transfer handler for channel: {}", gameChannel);
//                return;
//            }
////            log.info("merchant before");
//            GlGameMerchant merchant = glGameMerchantMapper.getMerchantByChannelId(gameChannel);
//            if (merchant == null) {
//                log.warn("not valid merchant for channelId: {}", gameChannel);
//                return;
//            }
////            log.info("merchant after");
//            boolean status = transferHandler.doTransferStatus(merchant, transfer);
//            glTransferTransactionService.doExceptionApprove(transfer, status ? 1 : 0, "系统自动审核", "system");
//        } catch (Exception e) {
//            log.error("game transfer fix error", e);
//            throw new ServiceException(e);
//        }
    }
}
