package com.yx.basejobservice.service;

import com.yx.appgameservice.model.GlGameMerchant;
import com.yx.appgameservice.model.GlGameTransferException;
import com.yx.basecoreservice.exception.ServiceException;

/**
 * @Author Jesse
 * @Date 2019/10/19 10:57
 **/
public interface GameTransferService {
    /**
     * 检查转账状态
     *
     *
     * @param merchant
     * @param transfer
     * @return
     * @throws ServiceException
     */
    boolean doTransferStatus(GlGameMerchant merchant, GlGameTransferException transfer) throws ServiceException;
}
