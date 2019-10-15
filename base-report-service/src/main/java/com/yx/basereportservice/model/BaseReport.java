package com.yx.basereportservice.model;

import com.yx.basereportservice.enums.PlatformEnum;
import com.yx.basereportservice.enums.UserTypeEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BaseReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * msg
     */
    private Map<String, Object> msg;
    {
        msg = new HashMap<>();
    }

    public Map<String, Object> getMsg() {
        return this.msg;
    }

    public Object get(String k) {
        return this.msg.get(k);
    }

    public void set(String k, Object v) {
        this.msg.put(k, v);
    }

    /**
     * set index shard
     * @param date
     */
    public void setIndexShard(Date date) {
        String shard = DateFormatUtils.format(date, "yyyyMMdd");
        this.msg.put("index", shard);
    }

    /**
     * ip
     */
    public void setIp(String ip) {
        this.msg.put("ip", ip);
    }

    /**
     * 用户Id
     */
    public void setUid(Integer uid) {
        this.msg.put("uid", uid);
    }

    /**
     * primary key
     */
    public void setUuid(String uuid) {
        this.msg.put("uuid", uuid);
    }

    /**
     * 域名
     */
    public void setDomain(String domain) {
        this.msg.put("domain", domain);
    }


    /**
     * 用户类型
     */
    public void setUserType(UserTypeEnum type) {
        this.msg.put("userType", type);
    }

    /**
     * 用户名
     */
    public void setUserName(String userName) {
        this.msg.put("userName", userName);
    }

    /**
     * 设备ID
     */
    public void setDeviceId(String deviceId) {
        this.msg.put("deviceId", deviceId);
    }

    /**
     * 记录完结时间戳，finishTime
     */
    public void setFinishTime(Date finishTime) {
        this.msg.put("finishTime", finishTime);
    }

    /**
     * 该记录是否是虚拟数据
     */
    public void setIsFake(String isFake) { this.msg.put("isFake", isFake);}

    /**
     * timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.msg.put("timestamp", timestamp);
    }

    /**
     * platform
     */
    public void setPlatform(PlatformEnum platform) {
        this.msg.put("platform", platform);
    }

    /**
     * 所属上级ID
     */
    public void setParentId(Integer parentId) {
        this.msg.put("parentId", parentId);
    }

    /**
     * 所属上级
     */
    public void setParentName(String parentName) {
        this.msg.put("parentName", parentName);
    }

    // cohort属性
    // cohort分析设置
    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 注册时间
     */
    public void setRegTime(Date time) {
        this.msg.put("regTime", time);
    }

    /**
     * 注册天数
     */
    public void setRegDays(Long days) {
        this.msg.put("regDays", days);
    }

    /**
     * 注册周数
     */
    public void setRegWeeks(Long weeks) {
        this.msg.put("regWeeks", weeks);
    }

    /**
     * 注册月数
     */
    public void setRegMonths(Long months) {
        this.msg.put("regMonths", months);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
