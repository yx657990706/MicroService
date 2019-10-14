package com.yx.basecoreservice.enums;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/14
 * @time 3:58 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public enum EnumRegion {

    /**
     * 锁
     */
    MY_LOCK,
    /**
     * 验证码缓存域
     */
    CAPTCHA_REGION,
    /**
     * 微信缓存域
     */
    WECHAT_REGION,
    /**
     * 普通缓存域
     */
     NOMORL_REGION,
    /**
     * 特殊缓存域
     */
    SP_REGION;
    /**
     * 根据名字返回Region
     * @param regionName
     * @return
     */
    public static EnumRegion getRegionByName(String regionName) {
        EnumRegion regionEnum = null;
        for (EnumRegion r : EnumRegion.values()) {
            if (r.name().equals(regionName)) {
                regionEnum = r;
                break;
            }
        }
        return regionEnum;
    }
}
