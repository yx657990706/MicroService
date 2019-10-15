package com.yx.basereportservice.service;


import com.yx.basereportservice.model.QueueMessge;

/**
 * @author jesse
 * @version v1.0
 * @project mybase
 * @Description
 * @encoding UTF-8
 * @date 2018/12/18
 * @time 11:20
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public interface DealBizQueueService {
    /**
     * 处理消息业务
     * @param queueMessge
     */
    void process(final QueueMessge queueMessge);
}
