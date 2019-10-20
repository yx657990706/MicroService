package com.yx.basejobservice.service;

import com.yx.basecoreservice.exception.ServiceException;

/**
 * @Author Jesse
 * @Date 2019/10/20 14:54
 **/
public interface GameTransferProxyService {
    /**
     *  转账状态修复
     * @throws ServiceException
     */
    void doGameTransferFix() throws ServiceException;
}
