package com.yx.basejobservice.service.impl;

import com.yx.appgameservice.enums.GameChannelCode;
import com.yx.appgameservice.model.GlGameMerchant;
import com.yx.basecoreservice.exception.ServiceException;
import com.yx.basecoreservice.exception.enums.EnumExceptionResult;
import com.yx.basejobservice.service.GameHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @Author Jesse
 * @Date 2019/5/15 17:24
 **/
@Slf4j
@Service(GameChannelCode.CHANNEL_GAME_MW + "")
public class MWHistoryServiceImpl implements GameHistoryService {
    //private static final Integer GAME_ID = 23;
    private static final String SUCCESS  ="0000";//接口响应成功的代码

    @Override
    public Integer doGameHistory(GlGameMerchant merchant, Integer type, Date from, Date to, Date start) throws ServiceException {
        try {
            //封装报文

          //分页查询
            while (true) {
               //调用接口

                //解析响应

               //数据循环处理并上报

                //循环终止条件
            }
        } catch (Exception e) {
            log.error("MWFish doGameHistoryGet fault", e);
            throw new ServiceException(EnumExceptionResult.ERROR_UNKOWN);
        }
    }

}
