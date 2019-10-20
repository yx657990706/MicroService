package com.yx.appwebservice.controller;

import com.yx.basecoreservice.exception.ServiceException;
import com.yx.basecoreservice.model.MyResponse;
import com.yx.basecoreservice.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @Author Jesse
 * @Date 2019/10/20 14:37
 **/
@Api(tags = "钱包转账相关接口")
@Slf4j
@RestController
@RequestMapping("/treansfer")
public class GlTransferController {

    @ApiOperation(value = "提交转账（同步）", notes = "提交转账（同步）接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fromChannelId", value = "转出渠道：0中心钱包", required = true, dataType = "int"),
            @ApiImplicitParam(name = "toChannelId", value = "转入渠道：0中心钱包", required = true, dataType = "int"),
            @ApiImplicitParam(name = "amount", value = "转账金额", required = true, dataType = "decimal"),
            @ApiImplicitParam(name = "type", value = "类型:0(按指定金额传入，默认不传)，1(金额为转出账户最大金额)", required = true, dataType = "decimal")
    })
    @PostMapping("/submitsync")
    public MyResponse submitSync(@RequestParam Integer fromChannelId, @RequestParam Integer toChannelId,
                                 @RequestParam BigDecimal amount, @RequestHeader String token, @RequestHeader(required = false) String device_id,
                                 @RequestHeader(required = false, name = "device-id") String deviceId,
                                 @RequestParam(required = false, defaultValue = "0") Integer type,
                                 @RequestHeader(required = false) Integer os_type,
                                 @RequestHeader(required = false, name = "os-type") Integer osType,
                                 @RequestHeader(required = false, defaultValue = "0", name = "app-type") Integer appType,
                                 HttpServletRequest request) throws ServiceException {
        if (os_type == null) {
            os_type = osType;
        }
        if (device_id == null) {
            device_id = deviceId;
        }
//        GlUser user = glLoginService.findLoginUser(token);
//
//        //验证游戏平台是否开启（中心钱包例外）
//        if (! GameChannelCode.getGameStatusByChannel(fromChannelId) || ! GameChannelCode.getGameStatusByChannel(toChannelId)) {
//            return ResultGenerator.genFailResult("游戏关闭，转账失败");
//        }
//
//        //转账事件神策采集数据
//        TransferResult transferResult = new TransferResult();
//        transferResult.setTransfer_out_platform(GameChannelCode.convertChannelName(fromChannelId, appName));
//        transferResult.setTransfer_in_platform(GameChannelCode.convertChannelName(toChannelId, appName));
//        transferResult.setWhether_succeed(false);
//        transferResult.setPlatform_type(ConvertNameUtils.convertOsTypeName(os_type));
//        transferResult.setApp_name(ConvertNameUtils.convertAppTypeName(appType));
//        String failReason;
//
//        //虚拟用户限制转账
//        if ("1".equals(user.getIsFake())) {
//
//            //如果虚拟用户的关闭平台配置中，包含fromChannelId或者toChannelId，那么此次操作立刻失败
//            List<String> unsupportPlatforms = Lists.newArrayList(user.getUnsupportPlatform().split("/"));
//            if (unsupportPlatforms.contains(String.valueOf(fromChannelId).trim()) ||
//                    unsupportPlatforms.contains(String.valueOf(toChannelId).trim())) {
//                failReason = "网络故障无法打开页面";
//                transferResult.setFail_reason(failReason);
//                glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//                return ResultGenerator.genFailResult(failReason);
//            }
//        }
//        if (user.getUserType() != 0 || user.getStatus() != 0) {
//            transferResult.setFail_reason(ResultCode.TRANSFER_UNAVAILABLE.getMessage());
//            glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//            return ResultGenerator.genFailResult(ResultCode.TRANSFER_UNAVAILABLE.getCode(), ResultCode.TRANSFER_UNAVAILABLE.getMessage());
//        }
//        GlUserSecurity glUserSecurity = userSecurityService.findWithdrawSecurity(user.getId());
//        if (glUserSecurity != null && glUserSecurity.getWithdrawVal() == 2) {
//            transferResult.setFail_reason(ResultCode.TRANSFER_AMOUNT_LOCK.getMessage());
//            glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//            return ResultGenerator.genFailResult(ResultCode.TRANSFER_AMOUNT_LOCK.getCode(), ResultCode.TRANSFER_AMOUNT_LOCK.getMessage());
//        }
//        String ip = HttpUtils.getRequestIp(request);
////        GlGameTransferConfig config = glGameTransferService.getTransferConfig(user.getId());
//        Integer gameChannelId = fromChannelId == 0 ? toChannelId : fromChannelId;
//        if (glGameMaintenService.channelMainten(gameChannelId, os_type, appType)) {
//            failReason = "您有钱包正在维护，请确认后再进行转账";
//            transferResult.setFail_reason(failReason);
//            glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//            return ResultGenerator.genFailResult(failReason);
//        }
//        BigDecimal balance = BigDecimal.ZERO;
//        if (fromChannelId == 0) {//转出所有平台余额
//            balance = glFundUserAccountService.getUserBalance(user.getId());
//        } else { // 转入所有游戏余额
//            try {
//                balance = glGameService.doGameUserAndBalanceCheck(user.getId(), fromChannelId, ip);
//                //MW游戏钱包小于1返回
//                if (fromChannelId == GameChannelCode.CHANNEL_GAME_MW && balance.compareTo(BigDecimal.ONE) == -1) {
//                    return ResultGenerator.genSuccessResult();
//                }
//            } catch (Exception e) {
//                failReason = "游戏余额获取失败，请稍后再试";
//                transferResult.setFail_reason(failReason);
//                glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//                return ResultGenerator.genFailResult(failReason);
//            }
//        }
//        balance = balance.setScale(2, RoundingMode.DOWN);
//        amount = amount.setScale(2, RoundingMode.DOWN);
//        if (fromChannelId == GameChannelCode.CHANNEL_GAME_MW || toChannelId == GameChannelCode.CHANNEL_GAME_MW) {
//            balance = new BigDecimal(balance.intValue()).setScale(2, RoundingMode.DOWN);
//            amount = new BigDecimal(amount.intValue()).setScale(2, RoundingMode.DOWN);
//        }
//        if (balance.compareTo(BigDecimal.ZERO) != 1) {
//            return ResultGenerator.genSuccessResult();
//        }
//        if (type == 1) { // 全部转帐
//            amount = balance;
//        } else { // 指定金额转账
//            if (amount.compareTo(balance) == 1) {
//                failReason = "余额不足，转账失败";
//                transferResult.setFail_reason(failReason);
//                glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//                return ResultGenerator.genFailResult(failReason);
//            }
//        }
//        if (amount.compareTo(BigDecimal.ZERO) != 1) {
//            return ResultGenerator.genSuccessResult();
//        }
//        transferResult.setTransfer_amount(amount);
//        String url = HttpUtils.getUrl(request);
//        // 1. 生成并上报转账记录，如果是从中心钱包转出，先扣除余额
//        GlGameTransfer transfer = glGameService.doGameTransfer(user, fromChannelId, toChannelId, amount, os_type, device_id, url, ip);
//        if (!glTransferTransactionService.doGameTransfer(transfer, user, os_type, ip, device_id, url)) {
//            failReason = "中心钱包余额不足，转账失败";
//            transferResult.setFail_reason(failReason);
//            glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//            return ResultGenerator.genFailResult(failReason);
//        }
//        // 2. 调用三方转账接口：1成功，2失败，-1异常
//        int status = glGameService.doGameTransferSync(transfer, user, os_type, device_id, url, ip);
//        if (status != ProjectConstant.Status.SUCCESS) {
//            // 三方转账失败，更改转账状态并上报
//            transfer.setStatus(status);
//            glTransferTransactionService.doTransferFailed(transfer);
//            transferResult.setFail_reason(ResultCode.TRANSFER_ERROR.getMessage());
//            glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//            glGameService.doGameUserAndBalanceCheckWithoutKeyCheck(user.getId(), gameChannelId);
//            return ResultGenerator.genFailResult(ResultCode.TRANSFER_ERROR.getCode(), ResultCode.TRANSFER_ERROR.getMessage());
//        }
//        transferResult.setWhether_succeed(true);
//        glFundSensorsService.fundTrackSensors(user.getId().toString(), transferResult);
//        // 三方转账成功，更改转账状态并上报，如果是转入中心钱包，增加中心钱包余额
//        glTransferTransactionService.doTransferSuccess(transfer);
//        if (GameChannelCode.getCheckBalanceGameChannel().contains(gameChannelId)) {
//            glGameService.doGameUserAndBalanceCheckWithoutKeyCheck(user.getId(), gameChannelId);
//        }

        return ResponseUtil.success();
    }
}
