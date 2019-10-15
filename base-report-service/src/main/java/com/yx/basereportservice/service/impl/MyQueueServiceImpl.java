package com.yx.basereportservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.yx.basereportservice.constant.QueueMessgeConst;
import com.yx.basereportservice.model.QueueMessge;
import com.yx.basereportservice.service.DealBizQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/17
 * @time 5:41 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
@Service("myQueueService")
public class MyQueueServiceImpl implements DealBizQueueService {
    /**
     * 处理消息业务
     *
     * @param queueMessge
     */
    @Override
    public void process(QueueMessge queueMessge) {
        try {
            //获取队列中的数据
            final HashMap<String, Object> content = queueMessge.getContent();
//            final String openid = (String) content.get(QueueMessgeConst.OPENID_KEY);
            final String result = (String) content.get(QueueMessgeConst.BIZ_JSON_STRING_KEY);

            //将json转为对象进行业务处理

            log.info("===>>消费者myQueueService接收消息:{}",result);
        } catch (Exception e) {
            log.error("===>>myQueueService队列处理异常msg:{}",JSON.toJSONString(queueMessge),e);
        }
    }
}
