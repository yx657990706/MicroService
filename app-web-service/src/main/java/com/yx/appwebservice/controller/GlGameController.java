package com.yx.appwebservice.controller;

import com.yx.basecoreservice.model.MyResponse;
import com.yx.basecoreservice.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Jesse
 * @Date 2019/10/20 14:21
 **/
@Api(tags = "游戏相关接口")
@Slf4j
@RestController
@RequestMapping("/game")
public class GlGameController {
    /**
     * 进入游戏
     */
    @ApiOperation(value = "启动游戏", notes = "启动游戏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId", value = "游戏ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "type", value = "是否试玩 ：Normal(0) PlayForFun(1) ", required = true, dataType = "int")
    })
    @RequestMapping(value = "/jump", method = {RequestMethod.POST, RequestMethod.GET})
    public MyResponse jump(
            @RequestHeader String token, @RequestHeader(required = false) Integer os_type, // 客户端类型：0PC，1H5，2安卓，3IOS，4PAD
            @RequestHeader(required = false, name = "os-type") Integer osType,
            @RequestHeader(defaultValue = "0", required = false, name = "app-type") Integer appType, // 0不适用，1体育，2娱乐
            @RequestParam(value = "gameId", required = true) Integer gameId, // 游戏ID
            @RequestParam(value = "type", required = true, defaultValue = "0") Integer type, // 0:正式, 1:试玩 | XJ : Normal(0) PlayForFun(1)
            @RequestParam(value = "isJumpXJPc", required = false, defaultValue = "false") Boolean isJumpXJPc, // 是否H5強轉小金PC跳轉
            // @RequestParam(value = "device", required = true, defaultValue = "0") Integer device, // 0:PC,1：mobile | XJ : NA(0) WebSite(10) MobileWebStie(11) WebFlash(12) AndroidApp(20) iOSApp(21) Nano(31) Mini(32) Download(41)
            // 3:H5
            HttpServletRequest request) {
        try {
            if (os_type == null) {
                os_type = osType;
            }

//            GlUser user = glLoginService.findLoginUser(token);
//            String returnUrl = "";
//            if (user == null) {
//                returnUrl = "http://" + request.getHeader("Host").trim() + "/game_error.html?type=0";
//                return ResultGenerator.genSuccessResult(returnUrl);
//            }
//            // 五秒不让连续跳 3次以上 - 防止错误太大频率调用
//            if (!isFrequencyAvaliable(user.getId())) {
//                returnUrl = "http://" + request.getHeader("Host").trim() + "/game_error.html?type=0";
//                return ResultGenerator.genSuccessResult(returnUrl);
//            }
//            // 资金锁定，不能进游戏
//            GlUserSecurity glUserSecurity = glUserSecurityService.findWithdrawSecurity(user.getId());
//            if (glUserSecurity != null && glUserSecurity.getWithdrawVal() == 2) {
//                returnUrl = "http://" + request.getHeader("Host").trim() + "/game_error.html?type=0";
//                return ResultGenerator.genSuccessResult(returnUrl);
//            }
//            String ip = HttpUtils.getRequestIp(request);
////            if (gameId == 3) {
////                log.warn("xj login ip: {}, os_type: {},  user: {}", ip, os_type.toString(), user.getUsername());
////            }
//            //判断是否在维护中
//            if (glGameMaintenService.gameMainten(gameId, os_type, appType)) {
//                return ResultGenerator.genFailResult("该游戏正在维护，请稍后再试!");
//            }
//
//            if(os_type == 1 && isJumpXJPc){
//                os_type = 99;// H5轉PC
//            }
//            String url = glGameService.doGameUrlGenerate(user.getId(), gameId, type, os_type, ip,"https://" + request.getHeader("Host").trim(),appType);
//
//            if (StringUtils.isNotEmpty(url)) {
//                return ResultGenerator.genSuccessResult(url);
//            } else {
//                returnUrl = "http://" + request.getHeader("Host").trim() + "/game_error.html?type=1";
//                return ResultGenerator.genSuccessResult(returnUrl);
//            }
            return ResponseUtil.success();
        } catch (Exception e) {
            log.error("game jump error", e);
            return ResponseUtil.error("1001", e.getMessage());
        }
    }
}
