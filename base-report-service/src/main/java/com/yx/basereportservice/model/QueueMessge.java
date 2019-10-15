package com.yx.basereportservice.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/17
 * @time 5:08 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p> 自定义队列传输对象
 * --------------------------------------------------
 * </pre>
 */
public class QueueMessge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息内容
     */
    private HashMap<String, Object> content = new HashMap<String, Object>();


    public HashMap<String, Object> getContent() {
        return content;
    }

    public void setContent(HashMap<String, Object> content) {
        this.content = content;
    }

    public void put(String key, Object obj) {
        this.getContent().put(key, obj);
    }

    public Object get(String key) {
        return this.getContent().get(key);
    }

    public String toJsonString() {
        return JSON.toJSONString(content);
    }
}
