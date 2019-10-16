package com.yx.basecoreservice.exception;

import com.yx.basecoreservice.exception.enums.EnumExceptionResult;
import com.yx.basecoreservice.model.MyResponse;
import com.yx.basecoreservice.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/14
 * @time 10:26 AM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public MyResponse handle(Exception e) {
        if(e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            log.error("自定义异常 {}:{}", serviceException.getCode(),e.getMessage(),e);
            return ResponseUtil.error(serviceException.getCode(), serviceException.getMessage());
        }else {
            //日志记录异常信息,便于排查问题
            log.error("系统异常 {}:{}", EnumExceptionResult.ERROR_UNKOWN.getCode(),e.getMessage(),e);
            return ResponseUtil.error(EnumExceptionResult.ERROR_UNKOWN.getCode(),EnumExceptionResult.ERROR_UNKOWN.getMsg());
        }
    }
}
