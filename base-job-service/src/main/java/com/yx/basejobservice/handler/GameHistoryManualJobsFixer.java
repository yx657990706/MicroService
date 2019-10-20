package com.yx.basejobservice.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@JobHandler("GameHistoryManualJobsFixer")
public class GameHistoryManualJobsFixer extends IJobHandler {

//    @Reference(version = "1.0.0", retries = 3, timeout = 60000)
//    private GlGameHistoryManualJobsService gameHistoryManualJobsService;
//    @Autowired
//    private Map<String, GameHistoryHandler> gameHistoryHandlerMap;
//    @Resource
//    private GlGameMerchantMapper glGameMerchantMapper;
//    @Resource
//    private AGRealHistoryHandler agRealHistoryHandler;
//    @Resource
//    private AGFishHistoryHandler agFishHistoryHandler;
//    @Resource
//    private AGXinHistoryHandler agXinHistoryHandler;
//    @Resource
//    private AGYoplayHistoryHandler agYoplayHistoryHandler;

//    private final String OLD_AG_TYPE = "99";

    //    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public ReturnT<String> execute(String param) throws Exception {

//        int id = 0;
//        Integer count = null;
//        try {
//            //获取待处理的任务
//            GlGameHistoryManualJobs gameHistoryManualJobs = gameHistoryManualJobsService.getOneJob();
//            if (gameHistoryManualJobs == null) {
//                return;
//            }
//            Date startTime = new Date();
//            id = gameHistoryManualJobs.getRid();
//            log.info("===>>补录任务id：{}  信息：{} 开始执行", id, JSON.toJSONString(gameHistoryManualJobs));
//            //任务状态设置为处理中并更新任务开始时间
////            gameHistoryManualJobsService.updateStatusById(id, 9, new Date(), null, null);
//
//            //根据channelId+gameType判断handler
//            if (gameHistoryManualJobs.getChannelId() == GameChannelCode.CHANNEL_AG) {
//                //AG渠道处理
//                count = this.dealChannelAG(gameHistoryManualJobs);
//            } else {
//                //非AG渠道处理
//                count = this.dealChannelNotAG(gameHistoryManualJobs);
//            }
//            //将任务状态设置为成功并更新任务截止时间
//            gameHistoryManualJobsService.updateStatusById(gameHistoryManualJobs.getRid(), 1, startTime, new Date(), count);
//
//            log.info("===>>补录任务id：{} 执行结束", id);
//        } catch (Exception e) {
//            //将任务状态设置为异常
//            gameHistoryManualJobsService.updateStatusById(id, 2, null, null, 0);
//            log.error("GameHistoryManualJobsFixer.doGameHistoryManualJobsFix ERROR", e);
//        }
        return ReturnT.SUCCESS;
    }

//    /**
//     * 非AG渠道的任务处理
//     * <br>由于贝博体育API时间间隔问题，XJ_188_贝博体育在XJHistoryScheduler中处理
//     *
//     * @param gameHistoryManualJobs
//     * @throws Exception
//     */
//    private Integer dealChannelNotAG(GlGameHistoryManualJobs gameHistoryManualJobs) throws Exception {
//        Integer channelId = gameHistoryManualJobs.getChannelId();
//        String handlerId = channelId + "";
//        GameHistoryHandler handler = gameHistoryHandlerMap.get(handlerId);
//        //调用相应的handler进行处理
//        if (handler == null) {
//            log.warn("no game history handler for handlerId: {}, break history sync.", handlerId);
//            return 0;
//        }
//        GlGameMerchant merchant = glGameMerchantMapper.getMerchantByChannelId(channelId);
//        if (merchant == null) {
//            log.warn("not valid merchant for channelId: {}, break history sync.", channelId);
//            return 0;
//        }
//        Date from = gameHistoryManualJobs.getStartTime();
//        Date to = gameHistoryManualJobs.getEndTime();
//        Integer type = 8;
//        //数据类型特殊处理  1投注时间 2派彩时间  3非六合彩 4六合彩  5未结算 6已结算
//        String dataType = gameHistoryManualJobs.getDataType();
//        if (GameChannelCode.CHANNEL_LB == channelId) {
//            if ("1".equals(dataType)) {
//                type = 1;//投注时间   historyRecord   历史注单
//            } else {
//                type = 8;//派彩时间   historyRecord   历史注单
//            }
//        } else if (GameChannelCode.CHANNEL_5GM_LTY == channelId) {
//            if ("3".equals(dataType)) {
//                type = 0; //非六合彩
//            } else {
//                type = 1;//六合彩
//            }
//        }else if (GameChannelCode.CHANNEL_VR == channelId) {
//            if ("3".equals(dataType)) {
//                type = 0; //非六合彩
//            } else {
//                type = 1;//六合彩
//            }
//        }else if (GameChannelCode.CHANNEL_IM_ESPORT == channelId) {
//            if ("5".equals(dataType)) {
//                type = 0;//未结算
//            } else {
//                type = 1;//已结算
//            }
//        }
//        return handler.doGameHistory(merchant, type, from, to, new Date());
//    }
//
//    /**
//     * AG渠道的任务处理
//     *
//     * @param gameHistoryManualJobs
//     * @throws Exception
//     */
//    private Integer dealChannelAG(GlGameHistoryManualJobs gameHistoryManualJobs) throws Exception {
//             int count = 0;
//             int n = 0;
//            //根据gamSubType判断具体的handler
//            Integer gameType = gameHistoryManualJobs.getGameType();
//            Integer gamSubType = gameHistoryManualJobs.getGameSubType();
//            //确定游戏类别 ，再确定文件方式（目录或者文件）
//            String fileFolder = gameHistoryManualJobs.getFileFolder();
//            String fileNames = gameHistoryManualJobs.getFileName();
//            List<String> fileNameList = null;
//            List<String> path = null;
//            //组装文件的绝对路径
//            if (StringUtils.isNotBlank(fileNames)) {
//                fileNameList = JSON.parseArray(fileNames, String.class);
//                path = new ArrayList<>(fileNameList.size());
//                for (String fileName : fileNameList) {
//                    path.add(fileFolder + fileName);
//                }
//            }
//
//            String dataType = gameHistoryManualJobs.getDataType();
//            //老虎机
//            if (gameType == 3) {
//                //0AG老虎机 1XIN老虎机 2yoplay老虎机
//                if (0 == gamSubType) {
//                    //文件名为空，按路径扫描
//                    if (StringUtils.isBlank(fileNames)) {
//                        if(StringUtils.equals(dataType,OLD_AG_TYPE)){
//                            count = agRealHistoryHandler.doOldAGGameHistoryByFilePath(fileFolder);
//                        }else{
//                            count = agRealHistoryHandler.doGameHistoryByFilePath(fileFolder);
//                        }
//                        //按文件绝对路径扫描
//                    } else {
//                        for (String filePath : path) {
//                            count += agRealHistoryHandler.doGameHistory(filePath);
//                            n++;
//
//                        }
//                        log.info("===>>补录任务id：{} agTiger共{}个文件，当前完成第{}个文件,count:{} ",gameHistoryManualJobs.getRid(),path.size(),n,count);
//                    }
//                } else if (1 == gamSubType) {
//                    if (StringUtils.isBlank(fileNames)) {
//                        if(StringUtils.equals(dataType,OLD_AG_TYPE)){
//                            count = agXinHistoryHandler.doOldAGGameHistoryByFilePath(fileFolder);
//                        }else{
//                            count = agXinHistoryHandler.doGameHistoryByFilePath(fileFolder);
//                        }
//                    } else {
//                        for (String filePath : path) {
//                            count += agXinHistoryHandler.doGameHistory(filePath);
//                            n++;
//                        }
//                        log.info("===>>补录任务id：{} agXin共{}个文件，当前处理第{}个文件,count:{}",gameHistoryManualJobs.getRid(),path.size(),n,count);
//                    }
//                } else if (2 == gamSubType) {
//                    if (StringUtils.isBlank(fileNames)) {
//                        if(StringUtils.equals(dataType,OLD_AG_TYPE)){
//                            count = agYoplayHistoryHandler.doOldAGGameHistoryByFilePath(fileFolder);
//                        }else{
//                            count = agYoplayHistoryHandler.doGameHistoryByFilePath(fileFolder);
//                        }
//                    } else {
//                        for (String filePath : path) {
//                            count += agYoplayHistoryHandler.doGameHistory(filePath);
//                            n++;
//                        }
//                        log.info("===>>补录任务id：{} agYoplay共{}个文件，当完成第{}个文件,count:{}",gameHistoryManualJobs.getRid(),path.size(),n,count);
//                    }
//                }
//            } else if (gameType == 1) {//真人
//                if (StringUtils.isBlank(fileNames)) {
//                    if(StringUtils.equals(dataType,OLD_AG_TYPE)){
//                        count = agRealHistoryHandler.doOldAGGameHistoryByFilePath(fileFolder);
//                    }else{
//                        count = agRealHistoryHandler.doGameHistoryByFilePath(fileFolder);
//                    }
//                } else {
//                    for (String filePath : path) {
//                        count += agRealHistoryHandler.doGameHistory(filePath);
//                        n++;
//                    }
//                    log.info("===>>补录任务id：{} agReal共{}个文件，当前完成第{}个文件,count:{}",gameHistoryManualJobs.getRid(),path.size(),n,count);
//                }
//            } else if (gameType == 4) {//捕鱼
//                if (StringUtils.isBlank(fileNames)) {
//                    if(StringUtils.equals(dataType,OLD_AG_TYPE)){
//                        count = agFishHistoryHandler.doOldAGGameHistoryByFilePath(fileFolder);
//                    }else{
//                        count = agFishHistoryHandler.doGameHistoryByFilePath(fileFolder);
//                    }
//
//                } else {
//                    for (String filePath : path) {
//                        count += agFishHistoryHandler.doGameHistory(filePath);
//                        n++;
//                    }
//                    log.info("===>>补录任务id：{} agFish共{}个文件，当前完成第{}个文件,count:{}",gameHistoryManualJobs.getRid(),path.size(),n,count);
//                }
//            }
//            return count;
//    }
}
