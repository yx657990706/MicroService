package com.yx.basejobservice.service.impl;

import com.yx.appgameservice.enums.GameChannelCode;
import com.yx.appgameservice.model.GlGameMerchant;
import com.yx.appgameservice.model.GlGameTransferException;
import com.yx.basecoreservice.exception.ServiceException;
import com.yx.basecoreservice.exception.enums.EnumExceptionResult;
import com.yx.basejobservice.service.GameTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Jesse
 * @Date 2019/10/19 10:58
 **/
@Slf4j
@Service("transferHandler" + GameChannelCode.CHANNEL_GAME_MW)
public class MWTransferServiceImpl implements GameTransferService {
    /**
     * 检查转账状态
     *
     * @param merchant
     * @param transfer
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean doTransferStatus(GlGameMerchant merchant, GlGameTransferException transfer) throws ServiceException {
        try {

            //封装报文，调用MW的转账准备接口
            //解析报文，并处理特殊情形
//            if(StringUtils.isBlank(result)){
//                throw new RuntimeException();
//            }
//            log.info("orderState result : "+result);
//            JSONObject json = JSON.parseObject(result);
//            String retStatus = json.getString("ret");
//
//            //訂單不存在
//            if(StringUtils.equals(retStatus,"1008")){
//                return false;
//            }
//
//            //不成功
//            if(!StringUtils.equals(retStatus,"0000")){
//                throw new RuntimeException();
//            }
//
//            Integer orderState= json.getInteger("orderState");
//            //processing = 0
//            if(orderState == 0){
//                throw new RuntimeException("doTransferStatus is Process");
//            }
//            //success = 1
            return  true;
        } catch (Exception e) {
            log.error("MW MWTransferHandler", e);
            throw new ServiceException(EnumExceptionResult.ERROR_UNKOWN);
        }
    }
}
