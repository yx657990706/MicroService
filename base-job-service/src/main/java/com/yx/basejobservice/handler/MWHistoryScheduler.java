package com.yx.basejobservice.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.yx.appgameservice.enums.GameChannelCode;
import com.yx.basejobservice.model.GlGameBetSyncLog;
import com.yx.basejobservice.service.GameHistoryProxyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author Allen
 * @Date 2019/6/5 17:19
 **/
@Slf4j
@Component
@JobHandler("MWHistoryScheduler")
public class MWHistoryScheduler extends IJobHandler {
    @Resource
    private GameHistoryProxyService gameHistoryProxyService;


    private final int reqRange = 5;//数据拉取最大区间是5分钟，允许1周内的数据
    private final int baseStartTime = 5;//数据延迟时间（分钟）
    private final int type = 1;

    /*@Scheduled(fixedDelay = 1000 * 16, initialDelay = 8000)*/
    public ReturnT<String> execute(String param) throws Exception {
        try {
            Date now = new Date();

            //TODO 任务日志
            GlGameBetSyncLog lastSyncLog = null;//gameHistoryService.getLastSyncLog(GameChannelCode.GAME_MW.getChannel(), type);

            Date end = DateUtils.addMinutes(now, -baseStartTime);
            Date from = null;
            Date to = null;
            if (lastSyncLog != null) {
                from = DateUtils.addMinutes(lastSyncLog.getToTime(), -1);//有记录，拉1分钟重合数据，保证不掉单
            } else {
                from = DateUtils.addMinutes(end, -reqRange);
            }
            if (from.after(end)) {
                return ReturnT.SUCCESS;
            }
            to = DateUtils.addMinutes(from, reqRange);
            if (to.after(end)) {
                to = end;
            }
            log.info("MWHistoryScheduler task start：from {} to {}", from.toString(), to.toString());
            gameHistoryProxyService.doGameHistory(GameChannelCode.GAME_MW.getChannel(), type, from, to, now);
            log.info("MWHistoryScheduler task finish：from {} to {}", from.toString(), to.toString());

            gameHistoryProxyService.createSyncLog(GameChannelCode.GAME_MW.getChannel(), type, from, to, now);
        } catch (Exception e) {
            log.error("MWHistoryScheduler ERROR", e);
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }
}
