package com.yx.appclient.mytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2019/2/18
 * @time 5:11 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@RefreshScope//配置文件动态刷新
@RestController
public class HelloController {

    @Autowired
    Environment env;

    @Value("${leo}")
    String sang;

    @RequestMapping("/sang")
    public String sang() {
        return this.sang;
    }
    @RequestMapping("/sang2")
    public String sang2() {
        return env.getProperty("leo", "未定义");
    }
}
