//package com.yx.appgameservice.service.impl;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.dubbo.config.annotation.Service;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.betball.core.constant.ProjectConstant;
//import com.betball.core.enums.DeviceEnum;
//import com.betball.core.enums.GlActionEnum;
//import com.betball.core.exception.ServiceException;
//import com.betball.core.json.JsonPath;
//import com.betball.core.model.GlRequestHeader;
//import com.betball.core.okhttp.OkHttpUtil;
//import com.betball.core.service.redis.RedisService;
//import com.betball.core.service.redis.impl.RedisKeyHelper;
//import com.betball.core.utils.DateUtils;
//import com.betball.core.utils.GameChannelCode;
//import com.betball.core.utils.MD5;
//import com.betball.core.utils.NumStringUtils;
//import com.betball.fund.service.GlFundUserAccountService;
//import com.betball.gamebet.dao.GlGameTransferrespMapper;
//import com.betball.gamebet.dao.GlGameUserMapper;
//import com.betball.gamebet.model.*;
//import com.betball.gamebet.service.GlGameApiService;
//import com.betball.gamebet.service.GlGameService;
//import com.betball.gamebet.service.GlGameUserService;
//import com.betball.gamebet.utils.MWUtil;
//import com.betball.push.Channel;
//import com.betball.push.GlPushClient;
//import com.betball.report.Enum.UserTypeEnum;
//import com.betball.report.model.GameUserBalanceReport;
//import com.betball.report.service.GlReportGameUserBalanceService;
//import com.betball.user.model.GlUser;
//import com.betball.user.service.GlUserRelationService;
//import com.betball.user.service.GlUserService;
//import com.betball.userdata.service.GlUserDataService;
//import com.google.common.collect.Lists;
//import com.jayway.jsonpath.DocumentContext;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.*;
//
//import static com.betball.core.service.redis.impl.RedisKeyHelper.GAME_BALANCE_CACHE;
//
///**
// * @Author Jesse
// * @Date 2019/5/6 14:37
// **/
//@Slf4j
//@Service
//public class GlMwGameApiServiceImpl implements GlGameApiService {
//    private Timer reportTimer = null;
//    private static final String SUCCESS  ="0000";//接口响应成功的代码
//    @Resource
//    private OkHttpUtil okHttpUtil;
//    @Resource
//    private RedisService redisService;
//    @Resource
//    private GlGameTransferrespMapper gameTransferResponseMapper;
//    @Resource
//    private GlGameUserMapper glGameUserMapper;
//    @Resource
//    private GlGameUserService gameUserService;
//    @Reference
//    private GlUserDataService glUserDataService;
//
//    @Reference(version = "1.0.0", retries = 3, timeout = 60000)
//    private GlUserService userService;
//    @Reference(version = "1.0.0", retries = 3, timeout = 60000)
//    private GlFundUserAccountService userFundAccountService;
//    @Reference(version = "1.0.0", retries = 3, timeout = 60000)
//    private GlUserRelationService userRelationService;
//    @Reference(version = "1.0.0", retries = 3, timeout = 60000)
//    private GlReportGameUserBalanceService glReportGameUserBalanceService;
//
//    @Resource
//    private GlPushClient glPushClient;
//
//
//    @Resource
//    private GlGameService glGameService;
//
//    @PostConstruct
//    public void init() throws Exception {
//        reportTimer = new Timer(true);
//    }
//
//    @Override
//    public BigDecimal doGameUserBlanceUpdate(Integer userId, Integer channelId, GlGameMerchant merchant) throws ServiceException {
//        try {
//            GlGameUser key = new GlGameUser();
//            key.setChannelId(channelId);
//            key.setUserId(userId);
//            //查询游戏账户信息
//            GlGameUser user = gameUserService.getGlGameUser(key);
//            if (user == null) {
//                return BigDecimal.ZERO;
//            }
//            String domainUrl = this.getDomainUrl(merchant.getAuthToken(), merchant.getDomain(), merchant.getPrivateKey(), merchant.getUri());
//            if (StringUtils.isBlank(domainUrl)) {
//                return user.getBalance();
//            }
//            String apiURL = MWUtil.getWMApiUrl(merchant.getUri(), domainUrl, MWUtil.FUNC_USERINFO);
//            //查询第三方游戏账户余额
//            String responseBody = this.getUserInfo(merchant.getMerchantCode()+user.getUsername(), user.getPassword(), apiURL, merchant.getMerchantCode(), merchant.getDomain(), merchant.getPrivateKey(), merchant.getAuthToken());
//            if (StringUtils.isBlank(responseBody)) {
//                return user.getBalance();
//            }
//            String ret = JsonPath.$$(responseBody).read("$.ret", String.class);
//            if (!MWUtil.SUCCESS.equals(ret)) {
//                log.warn("===>>MW userInfo异常 ret:{}  msg:{}", ret, JsonPath.$$(responseBody).read("$.msg", String.class));
//                return user.getBalance();
//            }
//
//            JSONObject jsonObject = JSONObject.parseObject(responseBody);
//            String userInfo = jsonObject.getString("userInfo");
//            String balance = JSONObject.parseObject(userInfo).getString("money");
//
//
//            GlGameUser record = new GlGameUser();
//            record.setBalance(new BigDecimal(balance));
//            record.setChannelId(channelId);
//            record.setUserId(userId);
//
//            glGameUserMapper.updateBalance(record.getUserId(), record.getChannelId(), record.getBalance(), record.getBalance());
//
//            //上报中心余额变动
//            GlUser glUser = userService.findById(userId);
//            GameUserBalanceReport gameUserBalanceReport = new GameUserBalanceReport();
//            gameUserBalanceReport.setUuid(userId + "_" + channelId + "_" + DateUtils.format(new Date(), DateUtils.YYYYMMDD));
//            gameUserBalanceReport.setUserId(userId);
//            gameUserBalanceReport.setChannelId(channelId);
//            gameUserBalanceReport.setBalance(record.getBalance());
//            gameUserBalanceReport.setCreateTime(new Date());
//            gameUserBalanceReport.setUserName(glUser.getUsername());
//            gameUserBalanceReport.setUid(userId);
//            gameUserBalanceReport.setUserType(UserTypeEnum.valueOf(glUser.getUserType()));
//            gameUserBalanceReport.setParentId(glUser.getParentId());
//            gameUserBalanceReport.setParentName(glUser.getParentName());
//            gameUserBalanceReport.setTimestamp(new Date());
//            glReportGameUserBalanceService.report(gameUserBalanceReport);
//            glPushClient.send(Channel.GameBalanceChange, userId, gameUserBalanceReport);
//            return record.getBalance();
//
//        } catch (Exception e) {
//            throw new ServiceException(e.getMessage(), e);
//        }
//    }
//
//    @Override
//    public GlGameUser doGameUserCreate(Integer userId, Integer channelId, GlGameMerchant merchant, String ip) throws ServiceException {
//        try {
//            GlUser user = userService.findById(userId);
//            if (user == null) {
//                return null;
//            }
//            // 一分钟内不创建用户 - 防止错误太大频率调用
//            Date now = new Date();
//            String lastCreateTime = redisService.get("@" + userId + "@" + channelId + "@createGameUser@MW");
//            if (lastCreateTime != null) {
//                Long tokenTime = now.getTime() - Long.valueOf(lastCreateTime);
//                if (tokenTime < 600000) {
//                    return null;
//                }
//            }
//            return doGameUserCreateApi(user, channelId, merchant, 4, ip,MWUtil.DEFAULT_GAME_ID);//公共钱包，固定传一个gameId即可
//        } catch (Exception e) {
//            throw new ServiceException(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * @param tryCount 尝试建立次数
//     */
//    private GlGameUser doGameUserCreateApi(GlUser user, Integer channelId, GlGameMerchant merchant, Integer tryCount, String ip,String gameId) throws Exception {
//
//
//         if (tryCount == 0) {
//            return null;
//        }
//        //获取domainURL
//        String domainUrl = this.getDomainUrl(merchant.getAuthToken(), merchant.getDomain(), merchant.getPrivateKey(), merchant.getUri());
//        if (StringUtils.isBlank(domainUrl)) {
//            return null;
//        }
//        //测试环境和生产环境的用户名不能重合(MerchantCode在本游戏区分环境)
//          String  gameUserName =merchant.getMerchantCode()+ NumStringUtils.numToLowerString(user.getId(), 12 - tryCount);
//
//          String password = MD5.md5(gameUserName + merchant.getPrivateKey());
//        String responseBody = doGameUserCreateApi(gameUserName, password, merchant, domainUrl, merchant.getDomain(), merchant.getPrivateKey(), merchant.getAuthToken(),gameId,null);
//
//        boolean isSuccess = false;
//        String ret = "";
//        if (StringUtils.isNotBlank(responseBody)) {
//            ret = JsonPath.$$(responseBody).read("$.ret", String.class);
//            if (MWUtil.SUCCESS.equals(ret)) {
//                isSuccess = true;
//            } else {
//                log.error("===>>oauth异常ret:{}  msg:{}", ret, JsonPath.$$(responseBody).read("$.msg", String.class));
//            }
//        }
//        if (!isSuccess) {
//            if (tryCount == 1) {
//                log.error("create MW username error, error code: " + ret);
//            }
//            return doGameUserCreateApi(user, channelId, merchant, tryCount - 1, ip,gameId);
//        } else {
//            //缓存用户IP 3小时
//            redisService.set(RedisKeyHelper.LDTIGER_DOMAIN + "IP_" + user.getId(), ip, 3 * 60 * 60);
//            return gameUserService.initGameUser(user.getId(), gameUserName.replace(merchant.getMerchantCode(),""), channelId, password);
//        }
//    }
//
//    private String doGameUserCreateApi(String uid, String utoken, GlGameMerchant merchant, String domainUrl, String MWPublicKey, String ECPrivateKey, String site,String gameId,Integer device) throws ServiceException {
//        try {
//            GlGame glGame=glGameService.findById(gameId);
//             //获取apiURL
//            String apiURL = MWUtil.getWMApiUrl(merchant.getUri(), domainUrl, MWUtil.FUNC_OAUTH);
//            //封装报文
//            //data数据参数字典排序
//            TreeMap<String, Object> dataMap = new TreeMap<String, Object>();
//            Long timestamp = MWUtil.getUnixTimeStamp();
//            dataMap.put("uid", uid);
//            dataMap.put("utoken", utoken);
//            dataMap.put("timestamp", timestamp);
//            dataMap.put("jumpType", 0);
//            if(glGame==null){
//                dataMap.put("gameId", "1051");
//            }else{
//                dataMap.put("gameId", glGame.getGameCode());
//
//            }
//            dataMap.put("merchantLogoImg", "");
//            dataMap.put("currency", "CNY");
//            String EC_AES_key = MWUtil.getAesKey();
//            Map<String, String> paramsMap = MWUtil.getRequestParmasMap(site, EC_AES_key, ECPrivateKey, MWPublicKey, dataMap, MWUtil.FUNC_OAUTH);
//             //调用MW的认证接口
//            //String result = MWUtil.httpPostRequest(new URL(apiURL), paramsMap);
//            GlRequestHeader requestHeader = GlRequestHeader.builder()
//                    .action(GlActionEnum.GAME_JUMP.getCode())
//                    .channelId(GameChannelCode.CHANNEL_GAME_MW+"")
//                    .channelName(GameChannelCode.GAME_MW.getCode())
//                    .terminal(DeviceEnum.convertDeviceName(device))
//                    .build();
//            String result = okHttpUtil.post(apiURL,paramsMap,requestHeader);
//
//            return result;
//        } catch (Exception e) {
//            log.error("doGameUserCreateApi", e);
//            throw new ServiceException(e.getMessage(), e);
//        }
//    }
//
//
//    @Override
//    public boolean doGameTransfer(Integer userId, Integer fromChannelId, Integer toChannelId, String tradeId, BigDecimal amount, GlGameMerchant merchant) throws ServiceException {
//        try {
//             if (fromChannelId != 0 && toChannelId != 0) {
//                return false;
//            }
//
//            GlGameUserKey key = new GlGameUserKey();
//            if (fromChannelId == 0) {
//                key.setChannelId(toChannelId);
//            } else {
//                key.setChannelId(fromChannelId);
//            }
//            key.setUserId(userId);
//            GlGameUser gameUser = glGameUserMapper.selectForUpdate(key.getUserId(), key.getChannelId());
//            if (gameUser == null) {
//                return false;
//            }
//            //调用domain方法
//            String domainUrl = this.getDomainUrl(merchant.getAuthToken(), merchant.getDomain(), merchant.getPrivateKey(), merchant.getUri());
//            if (StringUtils.isBlank(domainUrl)) {
//                return false;
//            }
//            String transferOrderTime = DateUtils.formatForTime(new Date());//我方订单时间
//            //通过登录缓存获取登录IP
//            String ip = redisService.get(RedisKeyHelper.LDTIGER_DOMAIN + "IP_" + userId);
//            String transferClientIp = StringUtils.isNotBlank(ip) ? ip : "127.0.0.1";
//            String preBody = null;
//            String responseBody = null;
//            //MW只能转整数
//            String apiURL = MWUtil.getWMApiUrl(merchant.getUri(), domainUrl, MWUtil.FUNC_TRANSFERPREPARE);
//
//            String transferType=(fromChannelId==0?MWUtil.TRANSFERTYPE_DEPOSIT:MWUtil.TRANSFERTYPE_WITHDRAW);
//
//            preBody = this.transferPrepare(merchant.getMerchantCode()+gameUser.getUsername(), gameUser.getPassword(), transferType, amount.intValue(), tradeId, transferOrderTime, transferClientIp, merchant.getMerchantCode(), apiURL, merchant.getPublicKey(), merchant.getPrivateKey(), merchant.getAuthToken());
//                if (StringUtils.isBlank(preBody)) {
//                    return false;
//                }
//                String ret = JsonPath.$$(preBody).read("$.ret", String.class);
//                if (!MWUtil.SUCCESS.equals(ret)) {
//                    log.error("===>>MW 转账准备异常 ret:{}  msg:{}", ret, JsonPath.$$(preBody).read("$.msg", String.class));
//                    return false;
//                }
//
//            String asinTransferOrderNo = JsonPath.$$(preBody).read("$.asinTransferOrderNo", String.class);//MWG 订单号
//            String asinTransferDate = JsonPath.$$(preBody).read("$.asinTransferDate", String.class);//MWG 订单时间
//            //转账确认
//            String payApiURL = MWUtil.getWMApiUrl(merchant.getUri(), domainUrl, MWUtil.FUNC_TRANSFERPAY);
//            responseBody = transferPay(merchant.getMerchantCode()+gameUser.getUsername(), gameUser.getPassword(), asinTransferOrderNo, asinTransferDate, tradeId, amount.intValue() + "", transferClientIp, merchant.getMerchantCode(), payApiURL, merchant.getDomain(), merchant.getPrivateKey(), merchant.getAuthToken());
//             boolean isSuccess = false;
//            String code = null;
//            if (StringUtils.isNotEmpty(responseBody)) {
//                code = JsonPath.$$(responseBody).read("$.ret", String.class);
//                isSuccess = MWUtil.SUCCESS.equals(code);
//            }
//            if (!isSuccess) {
//                log.error("MW doGameTransfer fail errorCode: {}  msg:{}", code, JsonPath.$$(responseBody).read("$.msg", String.class));
//                saveGameTransferResponseRecord(null, tradeId, responseBody, 2, "ErrorCode: " + code);
//                return false;
//            }
//             //保存转账记录
//            saveGameTransferResponseRecord(null, tradeId, responseBody, 1, "updated");
//
//            String money = JsonPath.$$(responseBody).read("$.endMoney", String.class);//获取转账后的账户余额
////            String userInfoApiURL= MWUtil.getWMApiUrl(merchant.getUri(),domainUrl,MWUtil.FUNC_USERINFO);
////            //查询第三方游戏账户余额
////            String userInfoResponseBody = this.getUserInfo(user.getUsername(),user.getPassword(),userInfoApiURL,merchant.getMerchantCode(),merchant.getDomain(),merchant.getPrivateKey(),merchant.getAuthToken());
////            if (StringUtils.isNotBlank(userInfoResponseBody)) {
////                String  ret = JsonPath.$$(responseBody).read("$.ret", String.class);
////                if (!MWUtil.SUCCESS.equals(ret)) {
////                    log.warn("===>>MW userInfo异常 ret:{}  msg:{}", ret,JsonPath.$$(responseBody).read("$.msg", String.class));
////                }
////                money= JSON.parseObject(responseBody).getJSONArray("userInfo").getJSONObject(0).getString("money");
////            }
//
//            if (StringUtils.isNotBlank(money)) {
//                BigDecimal balance = BigDecimal.valueOf(Double.valueOf(money)).setScale(2, RoundingMode.DOWN);
//                glGameUserMapper.updateBalance(key.getUserId(), key.getChannelId(), balance, balance);
//                //上报中心余额变动
//                GlUser glUser = userService.findById(userId);
//                GameUserBalanceReport gameUserBalanceReport = new GameUserBalanceReport();
//                gameUserBalanceReport.setUuid(userId + "_" + key.getChannelId() + "_" + DateUtils.format(new Date(), DateUtils.YYYYMMDD));
//                gameUserBalanceReport.setUserId(userId);
//                gameUserBalanceReport.setChannelId(key.getChannelId());
//                gameUserBalanceReport.setBalance(balance);
//                gameUserBalanceReport.setCreateTime(new Date());
//                gameUserBalanceReport.setUserName(glUser.getUsername());
//                gameUserBalanceReport.setUid(userId);
//                gameUserBalanceReport.setUserType(UserTypeEnum.valueOf(glUser.getUserType()));
//                gameUserBalanceReport.setParentId(glUser.getParentId());
//                gameUserBalanceReport.setParentName(glUser.getParentName());
//                gameUserBalanceReport.setTimestamp(new Date());
//                glReportGameUserBalanceService.report(gameUserBalanceReport);
//                glPushClient.send(Channel.GameBalanceChange, userId, gameUserBalanceReport);
//
//                redisService.set(GAME_BALANCE_CACHE + userId + "_" + key.getChannelId(), balance, 5);
//            }
//            return true;
//        } catch (Exception e) {
//            log.error("MW transfer error: " + tradeId, e);
//            throw new ServiceException(e.getMessage(), e);
//        }
//    }
//
//    @Override
//    public int doGameTransfer(GlUser user, GlGameTransfer transfer, GlGameMerchant merchant) throws ServiceException {
//        try {
//            if (transfer.getFromChannelId() != 0 && transfer.getToChannelId() != 0) {
//                return ProjectConstant.Status.FAILED;
//            }
//
//            GlGameUserKey key = new GlGameUserKey();
//            if (transfer.getFromChannelId() == 0) {
//                key.setChannelId(transfer.getToChannelId());
//            } else {
//                key.setChannelId(transfer.getFromChannelId());
//            }
//            key.setUserId(user.getId());
//            GlGameUser gameUser = glGameUserMapper.selectForUpdate(key.getUserId(), key.getChannelId());
//            if (gameUser == null) {
//                return ProjectConstant.Status.FAILED;
//            }
//
//
//            //调用domain方法
//            String domainUrl = this.getDomainUrl(merchant.getAuthToken(), merchant.getDomain(), merchant.getPrivateKey(), merchant.getUri());
//            if (StringUtils.isBlank(domainUrl)) {
//                return ProjectConstant.Status.FAILED;
//            }
//            String transferOrderTime = DateUtils.formatForTime(new Date());//我方订单时间
//            //通过登录缓存获取登录IP
//            String ip = redisService.get(RedisKeyHelper.LDTIGER_DOMAIN + "IP_" + user.getId());
//            String transferClientIp = StringUtils.isNotBlank(ip) ? ip : "127.0.0.1";
//            String preBody = null;
//            String responseBody = null;
//            //MW只能转整数
//            String apiURL = MWUtil.getWMApiUrl(merchant.getUri(), domainUrl, MWUtil.FUNC_TRANSFERPREPARE);
//
//            String transferType=(transfer.getFromChannelId()==0?MWUtil.TRANSFERTYPE_DEPOSIT:MWUtil.TRANSFERTYPE_WITHDRAW);
//
//            preBody = this.transferPrepare(merchant.getMerchantCode()+gameUser.getUsername(), gameUser.getPassword(), transferType, transfer.getAmount().intValue(), transfer.getTradeId(), transferOrderTime, transferClientIp, merchant.getMerchantCode(), apiURL, merchant.getPublicKey(), merchant.getPrivateKey(), merchant.getAuthToken());
//            if (StringUtils.isBlank(preBody)) {
//                return ProjectConstant.Status.FAILED;
//            }
//            String ret = JsonPath.$$(preBody).read("$.ret", String.class);
//            if (!MWUtil.SUCCESS.equals(ret)) {
//                log.error("===>>MW 转账准备异常 ret:{}  msg:{}", ret, JsonPath.$$(preBody).read("$.msg", String.class));
//                return ProjectConstant.Status.FAILED;
//            }
//
//
//            String asinTransferOrderNo = JsonPath.$$(preBody).read("$.asinTransferOrderNo", String.class);//MWG 订单号
//            String asinTransferDate = JsonPath.$$(preBody).read("$.asinTransferDate", String.class);//MWG 订单时间
//            //转账确认
//            String payApiURL = MWUtil.getWMApiUrl(merchant.getUri(), domainUrl, MWUtil.FUNC_TRANSFERPAY);
//            responseBody = transferPay(merchant.getMerchantCode()+gameUser.getUsername(), gameUser.getPassword(), asinTransferOrderNo, asinTransferDate, transfer.getTradeId(), transfer.getAmount().intValue() + "", transferClientIp, merchant.getMerchantCode(), payApiURL, merchant.getDomain(), merchant.getPrivateKey(), merchant.getAuthToken());
//            //log.info("MW doGameTransfer responseBody: " + responseBody);
//
//            String code = null;
//            int status = ProjectConstant.Status.FAILED;
//            try {
//                if (StringUtils.isEmpty(responseBody)) {
//                    status = ProjectConstant.Status.ERROR;
//                } else {
//                    code = JsonPath.$$(responseBody).read("$.ret", String.class);
//                    if (MWUtil.SUCCESS.equals(code)) {
//                        status = ProjectConstant.Status.SUCCESS;
//                    }
//                }
//            } catch (Exception e) {
//                status = ProjectConstant.Status.ERROR;
//            }
//
//            if (status != ProjectConstant.Status.SUCCESS) {
//                saveGameTransferResponseRecord(asinTransferOrderNo, transfer.getTradeId(), responseBody, 2, "ErrorCode: " + code);
//                log.warn("MW transfer failed for order: {} with response: {}", transfer.getTradeId(), responseBody);
//                return status;
//            }
//            saveGameTransferResponseRecord(asinTransferOrderNo, transfer.getTradeId(), responseBody, 1, "updated");
//
//            String money = JsonPath.$$(responseBody).read("$.endMoney", String.class);//获取转账后的账户余额
////            String userInfoApiURL= MWUtil.getWMApiUrl(merchant.getUri(),domainUrl,MWUtil.FUNC_USERINFO);
////            //查询第三方游戏账户余额
////            String userInfoResponseBody = this.getUserInfo(user.getUsername(),user.getPassword(),userInfoApiURL,merchant.getMerchantCode(),merchant.getDomain(),merchant.getPrivateKey(),merchant.getAuthToken());
////            if (StringUtils.isNotBlank(userInfoResponseBody)) {
////                String  ret = JsonPath.$$(responseBody).read("$.ret", String.class);
////                if (!MWUtil.SUCCESS.equals(ret)) {
////                    log.warn("===>>MW userInfo异常 ret:{}  msg:{}", ret,JsonPath.$$(responseBody).read("$.msg", String.class));
////                }
////                money= JSON.parseObject(responseBody).getJSONArray("userInfo").getJSONObject(0).getString("money");
////            }
//            if (StringUtils.isNotBlank(money)) {
//                BigDecimal balance = BigDecimal.valueOf(Double.valueOf(money)).setScale(2, RoundingMode.DOWN);
//                glGameUserMapper.updateBalance(key.getUserId(), key.getChannelId(), balance, balance);
//                //上报中心余额变动
//                GameUserBalanceReport gameUserBalanceReport = new GameUserBalanceReport();
//                gameUserBalanceReport.setUuid(user.getId() + "_" + key.getChannelId() + "_" + DateUtils.format(new Date(), DateUtils.YYYYMMDD));
//                gameUserBalanceReport.setUserId(user.getId());
//                gameUserBalanceReport.setChannelId(key.getChannelId());
//                gameUserBalanceReport.setBalance(balance);
//                gameUserBalanceReport.setCreateTime(new Date());
//                gameUserBalanceReport.setUserName(user.getUsername());
//                gameUserBalanceReport.setUid(user.getId());
//                gameUserBalanceReport.setUserType(UserTypeEnum.valueOf(user.getUserType()));
//                gameUserBalanceReport.setParentId(user.getParentId());
//                gameUserBalanceReport.setParentName(user.getParentName());
//                gameUserBalanceReport.setTimestamp(new Date());
//                glReportGameUserBalanceService.report(gameUserBalanceReport);
//                glPushClient.send(Channel.GameBalanceChange, user.getId(), gameUserBalanceReport);
//
//            }
//            return status;
//        } catch (Exception e) {
//            log.error("MW transfer error: " + transfer.getTradeId(), e);
//            throw new ServiceException(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 保存转账记录
//     *
//     * @param outerTradeId
//     * @param tradeId
//     * @param resp
//     * @param status
//     * @param remark
//     */
//    private void saveGameTransferResponseRecord(String outerTradeId, String tradeId, String resp, int status, String remark) {
//        Date now = new Date();
//        GlGameTransferresp resRecord = new GlGameTransferresp();
//        resRecord.setCreateTime(now);
//        resRecord.setLastUpdate(now);
//        resRecord.setOuterTradeId(outerTradeId);
//        resRecord.setTradeId(tradeId);
//        resRecord.setResText(resp);
//        resRecord.setStatus(status);
//        resRecord.setRemark(remark);
//
//        // 如果已经有记录直接返回
//        GlGameTransferresp glGameTransferresp = gameTransferResponseMapper.selectByPrimaryKey(resRecord);
//        if (glGameTransferresp != null) return;
//
//        gameTransferResponseMapper.insertSelective(resRecord);
//    }
//
//    @Override
//    public String doGameUrlCheck(GlGameUser user, GlGame game, GlGameMerchant merchant, Integer type, Integer device, String ip, String domain, Integer appType) throws ServiceException {
//        //调用domain方法
//        String domainUrl = this.getDomainUrl(merchant.getAuthToken(), merchant.getDomain(), merchant.getPrivateKey(), merchant.getUri());
//        if (StringUtils.isBlank(domainUrl)) {
//            return null;
//        }
//        //调用认证方法获取接口参数
//        String responseBody = doGameUserCreateApi(merchant.getMerchantCode()+user.getUsername(), user.getPassword(), merchant, domainUrl, merchant.getDomain(), merchant.getPrivateKey(), merchant.getAuthToken(),String.valueOf(game.getGameId()),device);
//
//        if (StringUtils.isNotBlank(responseBody)) {
//            String ret = JsonPath.$$(responseBody).read("$.ret", String.class);
//            if (MWUtil.SUCCESS.equals(ret)) {
//                return domainUrl+ JsonPath.$$(responseBody).read("$.interface", String.class);
//            } else {
//                log.warn("===>>oauth异常ret:{}  msg:{}", ret, JsonPath.$$(responseBody).read("$.msg", String.class));
//            }
//        }
//        return null;
//    }
//
//
//    @Override
//    public List<GlGameBet> doGameHistoryGet(Integer channelId, String from, String to, Integer pageNumber, GlGameMerchant merchant, List<GlGameBet> historyList, Integer Type) throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public void changeUserPassword(GlGameMerchant gameMerchant, Integer channelId, String username, String password) throws ServiceException {
//        // 尚未有修改密码功能
//        throw new ServiceException("修改密码失败");
//    }
//
//    @Override
//    public String generatorUserName(Integer userId, Integer tryCount) {
//        return NumStringUtils.numToLowerString(userId, 12 - tryCount);
//    }
//
//    @Override
//    public void doHandTransferOut(String gameUserName, String password, String tradeId, BigDecimal amount, GlGameMerchant merchant) throws ServiceException {
//    }
//
//    @Override
//    public void doGameTokenCheck(GlGameMerchant merchant) throws ServiceException {
//        // do nothing
//    }
//
//    /**
//     * 获取domain地址
//     *
//     * @param site         站点ID
//     * @param MWPublicKey  MW公钥
//     * @param ECPrivateKey EC私钥
//     * @param url          domain请求地址
//     * @return
//     * @throws Exception
//     */
//    private String getDomainUrl(String site, String MWPublicKey, String ECPrivateKey, String url) {
//        try {
//
//            //从redis获取
//            String domainUrl = redisService.get(RedisKeyHelper.LDTIGER_DOMAIN + "domainURL");
//            if (StringUtils.isNotBlank(domainUrl)) {
//                return domainUrl;
//            } else {
//                String apiURL = MWUtil.getWMApiUrl(url, null, MWUtil.FUNC_DOMAIN);
//                TreeMap<String, Object> dataMap = new TreeMap<String, Object>();
//                Long timestamp = MWUtil.getUnixTimeStamp();
//                dataMap.put("timestamp", timestamp);//unix时间戳
//                //处理data参数
//                String EC_AES_key = MWUtil.getAesKey();
//                Map<String, String> paramsMap = MWUtil.getRequestParmasMap(site, EC_AES_key, ECPrivateKey, MWPublicKey, dataMap, MWUtil.FUNC_DOMAIN);
//                GlRequestHeader requestHeader = GlRequestHeader.builder()
//                        .action(GlActionEnum.GAME_JUMP.getCode())
//                        .channelId(GameChannelCode.CHANNEL_GAME_MW+"")
//                        .channelName(GameChannelCode.GAME_MW.getCode())
//                        .userId("")
//                        .userName("")
//                        .tradeId("")
//                        .build();
//                //String result = MWUtil.httpPostRequest(new URL(apiURL), paramsMap);
//                String result = okHttpUtil.post(apiURL,paramsMap,requestHeader);
//                  if (StringUtils.isNotBlank(result)) {
//                    String ret = JsonPath.$$(result).read("$.ret", String.class);
//                    if ("0000".equals(ret)) {
//                        domainUrl = JsonPath.$$(result).read("$.domain", String.class);
//                        redisService.set(RedisKeyHelper.LDTIGER_DOMAIN + "domainURL", domainUrl, 12 * 60 * 60);//缓存12小时
//                        return domainUrl;
//                    } else {
//                        log.error("===>>请求domain异常ret:{}  msg:{}", ret, JsonPath.$$(result).read("$.msg", String.class));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return null;
//    }
//
//    /**
//     * 查询用户角色信息（游戏账户余额）
//     *
//     * @param uid
//     * @param utoken
//     * @param apiURL
//     * @param merchantId
//     * @param MWPublicKey
//     * @param ECPrivateKey
//     * @param site
//     * @return
//     * @throws Exception
//     */
//    private String getUserInfo(String uid, String utoken, String apiURL, String merchantId, String MWPublicKey, String ECPrivateKey, String site) throws Exception {
//        TreeMap<String, Object> dataMap = new TreeMap<String, Object>();
//        Long timestamp = MWUtil.getUnixTimeStamp();
//        dataMap.put("uid", uid);//unix时间戳
//        dataMap.put("utoken", utoken);
//        //dataMap.put("merchantId", merchantId);
//        dataMap.put("timestamp", timestamp);
//        dataMap.put("currency", "CNY");
//        dataMap.put("getType", 1);//0 不返回货币单位 1 游戏信息数据单位为 MW 币(与CNY1:1) 2 游戏信息数据单位为用户注册货
//        String EC_AES_key = MWUtil.getAesKey();
//        Map<String, String> paramsMap = MWUtil.getRequestParmasMap(site, EC_AES_key, ECPrivateKey, MWPublicKey, dataMap, MWUtil.FUNC_USERINFO);
//        //String result = MWUtil.httpPostRequest(new URL(apiURL), paramsMap);
//        GlRequestHeader requestHeader = GlRequestHeader.builder()
//                .action(GlActionEnum.GAME_USER_BANLANCE_UPDATE.getCode())
//                .channelId(GameChannelCode.CHANNEL_GAME_MW+"")
//                .channelName(GameChannelCode.GAME_MW.getCode())
//                .userId("")
//                .userName("")
//                .tradeId("")
//                .build();
//        String result = okHttpUtil.post(apiURL,paramsMap,requestHeader);
//          return result;
//    }
//
//    /**
//     * 获取游戏列表
//     *
//     * @param site
//     * @param merchant
//     * @param deviceType 0:Flash 平台 1:H5 平台 2:APP 平台
//     * @return
//     * @throws Exception
//     */
//    private String getGameList(String site, GlGameMerchant merchant, String deviceType) throws Exception {
//         String domainUrl = redisService.get(RedisKeyHelper.LDTIGER_DOMAIN + "domainURL");
//          String apiURL =MWUtil.getWMApiUrl(merchant.getUri(), domainUrl, MWUtil.FUNC_GAMEINFO);
//        TreeMap<String, Object> dataMap = new TreeMap<String, Object>();
//        Long timestamp = MWUtil.getUnixTimeStamp();
//        dataMap.put("timestamp", timestamp);
//        dataMap.put("deviceType", deviceType);
//        String EC_AES_key = MWUtil.getAesKey();
//        Map<String, String> paramsMap = MWUtil.getRequestParmasMap(site, EC_AES_key, merchant.getPrivateKey(), merchant.getPublicKey(), dataMap, MWUtil.FUNC_GAMEINFO);
//        //String result = MWUtil.httpPostRequest(new URL(apiURL), paramsMap);
//        String result = okHttpUtil.post(apiURL,paramsMap);
//        return result;
//    }
//
//
//
//    /**
//     * 第三方转账准备接口
//     *
//     * @param uid
//     * @param utoken
//     * @param transferType
//     * @param transferAmount
//     * @param transferOrderNo
//     * @param transferOrderTime
//     * @param transferClientIp
//     * @param merchantId
//     * @param apiURL
//     * @param MWPublicKey
//     * @param ECPrivateKey
//     * @param site
//     * @return
//     * @throws ServiceException
//     */
//    private String transferPrepare(String uid, String utoken, String transferType, Integer transferAmount, String transferOrderNo, String transferOrderTime, String transferClientIp, String merchantId, String apiURL, String MWPublicKey, String ECPrivateKey, String site) throws ServiceException {
//        try {
//            //封装报文，调用MW的转账准备接口
//            TreeMap<String, Object> dataMap = new TreeMap<String, Object>();
//            Long timestamp = MWUtil.getUnixTimeStamp();
//            dataMap.put("uid", uid);
//            dataMap.put("utoken", utoken);
//            dataMap.put("transferType", transferType);
//            dataMap.put("transferAmount", transferAmount);
//            dataMap.put("transferOrderNo", transferOrderNo);
//            dataMap.put("transferOrderTime", transferOrderTime);
//            dataMap.put("transferClientIp", transferClientIp);
//            dataMap.put("merchantId", merchantId);
//            dataMap.put("timestamp", timestamp);
//            dataMap.put("currency", "CNY");
//            String EC_AES_key = MWUtil.getAesKey();
//            Map<String, String> paramsMap = MWUtil.getRequestParmasMap(site, EC_AES_key, ECPrivateKey, MWPublicKey, dataMap, MWUtil.FUNC_TRANSFERPREPARE);
//
//            GlRequestHeader requestHeader = GlRequestHeader.builder()
//                    .action(MWUtil.TRANSFERTYPE_DEPOSIT.equals(transferType)?GlActionEnum.DEPOSIT.getCode():GlActionEnum.WITHDRAW.getCode())
//                    .channelId(GameChannelCode.CHANNEL_GAME_MW+"")
//                    .channelName(GameChannelCode.GAME_MW.getCode())
//                    .userId("")
//                    .userName("")
//                    .tradeId(transferOrderNo)
//                    .build();
//            //String result = MWUtil.httpPostRequest(new URL(apiURL), paramsMap);
//            String result = okHttpUtil.post(apiURL,paramsMap,requestHeader);
//
//            return result;
//        } catch (Exception e) {
//            log.error("MW transferPrepare", e);
//            throw new ServiceException(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 第三方转账确认接口
//     *
//     * @param uid
//     * @param utoken
//     * @param asinTransferOrderNo
//     * @param asinTransferOrderTime
//     * @param transferOrderNo
//     * @param transferAmount
//     * @param transferClientIp
//     * @param merchantId
//     * @param apiURL
//     * @param MWPublicKey
//     * @param ECPrivateKey
//     * @param site
//     * @return
//     * @throws ServiceException
//     */
//    private String transferPay(String uid, String utoken, String asinTransferOrderNo, String asinTransferOrderTime, String transferOrderNo, String transferAmount, String transferClientIp, String merchantId, String apiURL, String MWPublicKey, String ECPrivateKey, String site) throws ServiceException {
//        try {
//            TreeMap<String, Object> dataMap = new TreeMap<String, Object>();
//            Long timestamp = MWUtil.getUnixTimeStamp();
//            dataMap.put("uid", uid);
//            dataMap.put("utoken", utoken);
//            dataMap.put("asinTransferOrderNo", asinTransferOrderNo);
//            dataMap.put("asinTransferOrderTime", asinTransferOrderTime);
//            dataMap.put("transferOrderNo", transferOrderNo);
//            dataMap.put("transferAmount", transferAmount);
//            dataMap.put("transferClientIp", transferClientIp);
//            // dataMap.put("merchantId", merchantId);
//            dataMap.put("timestamp", timestamp);
//            dataMap.put("currency", "CNY");
//            String EC_AES_key = MWUtil.getAesKey();
//            Map<String, String> paramsMap = MWUtil.getRequestParmasMap(site, EC_AES_key, ECPrivateKey, MWPublicKey, dataMap, MWUtil.FUNC_TRANSFERPAY);
//            GlRequestHeader requestHeader = GlRequestHeader.builder()
//                    .action(GlActionEnum.TRANSFER_CHECK.getCode())
//                    .channelId(GameChannelCode.CHANNEL_GAME_MW+"")
//                    .channelName(GameChannelCode.GAME_MW.getCode())
//                    .userId("")
//                    .userName("")
//                    .tradeId(transferOrderNo)
//                    .build();
//            //String result = MWUtil.httpPostRequest(new URL(apiURL), paramsMap);
//            String result = okHttpUtil.post(apiURL,paramsMap,requestHeader);
//
//            return result;
//        } catch (Exception e) {
//            log.error("MW transferPay", e);
//            throw new ServiceException(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 从第三方获取游戏记录
//     *
//     * @param request
//     * @param merchant
//     * @return
//     * @throws ServiceException
//     */
//    @Override
//    public JSONObject getGameHistory(GlGameHistoryQueryRequest request, GlGameMerchant merchant) throws ServiceException {
//        JSONObject jsonObject = new JSONObject();
//        List<Object> idList = Lists.newArrayList();
//
//        try {
//            Integer count = 0;
//            int page = 1;//当前页码
//            int totalSize  = 0;//信息总数
//            int totalPage = 0;//总页数
//            Date dayStart = DateUtils.getStartOfDay(request.getDealDate());//获取处理日期最早时间：00:00:00
//            Date dayEnd = DateUtils.getEndOfDay(request.getDealDate());//获取处理日期最晚时间：23:59:59
//
//            String domainURL = this.getDomainUrl(merchant.getAuthToken(),merchant.getDomain(),merchant.getPrivateKey(),merchant.getUri());
//            //获取apiURL
//            String apiURL = MWUtil.getWMApiUrl(merchant.getUri(),domainURL,MWUtil.FUNC_SITEUSERGAMELOG);
//            //封装报文  data数据参数字典排序
//            TreeMap<String, Object> dataMap = new TreeMap<String, Object>();
//            dataMap.put("beginTime", DateUtils.format(request.getFromTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
//            dataMap.put("endTime", DateUtils.format(request.getToTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
//            dataMap.put("iGetLogInfoType", "0");
//            dataMap.put("getType", "1");//0 不返回货币单位 1 游戏信息数据单位为 MW 币 2 游戏信息数据单位为用户注册货币
//            while (true) {
//                //移除重覆簽名
//                dataMap.remove("sign");
//                dataMap.put("page", page++);
//                String EC_AES_key = MWUtil.getAesKey();
//                Map<String, String> paramsMap = MWUtil.getRequestParmasMap(merchant.getAuthToken(),EC_AES_key,merchant.getPrivateKey(),merchant.getDomain(),dataMap,MWUtil.FUNC_SITEUSERGAMELOG);
////                String responseBody = MWUtil.httpPostRequest(new URL(apiURL),paramsMap);
//                log.info("===>>MW捕鱼注单请求报文：{}", JSONObject.toJSONString(paramsMap));
//                String responseBody = okHttpUtil.post(apiURL,paramsMap);
//
//                String code = JsonPath.$$(responseBody).read("$.ret", String.class);
////                String code = responseObject.getString("ret");
//                if (!SUCCESS.equals(code)) {
//                    log.error("MwGame_getGameHistory_ERROR_response: {},count: {}", responseBody,count);
//                    break;
//                }
//
//                DocumentContext dc = JsonPath.$$(responseBody);
//                JSONObject jsonBody = JSONObject.parseObject(responseBody);
//                JSONArray array =  jsonBody.getJSONArray("userGameLogs");
//                log.info("===>>MW捕鱼注单响应报文：{}", JSONObject.toJSONString(dc));
//                //当页数据量
//                Integer apiBetSize = dc.read("$.userGameLogs.length()");
//                totalSize  = dc.read("$.total");
//                //如果为0，则直接返回
//                if(totalSize == 0) break;
////                net.minidev.json.JSONArray userGameLogsArray = dc.read( "$.userGameLogs");
//                totalPage = totalSize/apiBetSize + (totalSize % apiBetSize >0?1:0);
//
//                for (int i = 0; i < apiBetSize; i++) {
//                    String betPath = "$.userGameLogs[" + i + "]";
//                    BigDecimal playMoney = new BigDecimal(dc.read(betPath + ".playMoney", String.class).replace(",",""));//玩家下注金额
//
//                    //根据目标时间过滤需要获取的注单号
//                    Date targetDate = DateUtils.parse(dc.read(betPath + ".logDate", String.class), DateUtils.YYYY_MM_DD_HH_MM_SS);//logDate是游戏结束的时间
//
//                    if(DateUtils.belongCalendar(targetDate, dayStart, dayEnd)){
//                        if(request.getType()==0){
//                            GameHistoryModel gameHistoryModel = new GameHistoryModel();
//                            gameHistoryModel.betId = dc.read(betPath + ".gameNum", String.class);
//                            gameHistoryModel.betAmount = playMoney;
//                            gameHistoryModel.betWinloss = BigDecimal.ZERO;
//                            idList.add(gameHistoryModel);
//                        }else {
//                            JSONObject jb = (JSONObject)array.get(i);
//                            idList.add(jb);
//                        }
//                    }
//                }
//
//                //循环终止条件
//                if(page>totalPage){
//                    break;
//                }
//            }
//
//            jsonObject.put("count", count);
//        } catch (Exception e) {
//            log.error("MwGame_getGameHistory_error: ", e);
//            throw new ServiceException(e.getMessage(), e);
//        }
//        return jsonObject;
//    }
//
//
//
//}
