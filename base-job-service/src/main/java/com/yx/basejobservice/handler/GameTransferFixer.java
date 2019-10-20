package com.yx.basejobservice.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.yx.basejobservice.service.GameTransferProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@JobHandler("GameTransferFixer")
public class GameTransferFixer extends IJobHandler {

    @Resource
    private GameTransferProxyService glGameTransferProxyService;

//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public ReturnT<String> execute(String param) throws Exception {
        try {
            glGameTransferProxyService.doGameTransferFix();
        } catch (Exception e) {
            log.error("GameTransferFixer.doGameTransferFix ERROR", e);
        }
        return SUCCESS;
    }
}
