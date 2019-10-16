package com.yx.appgameservice.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GlUser implements Serializable {

    private static final long serialVersionUID = -3458621218435389364L;

    private Integer id;

    /**
     * 用户类型：0玩家，1代理
     */
    private Integer userType;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String nickName;

    /**
     * 真实姓名
     */
    private String reallyName;

    /**
     * 1 男  2 女
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 手机号码归属地
     */
    private String place;

    /**
     * 头像
     */
    private String headUrl;


    /**
     * 生日
     */
    private String birthday;

    /**
     * 手机设备号
     */
    private String deviceId;

    /**
     * 注册ID
     */
    private String registerIp;
    /**
     * 上级代理ID
     */
    private Integer parentId;

    /**
     * 上级代理账号
     */
    private String parentName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户状态：通过二进制占位表示用户状态:000  正常 , 001 完全锁定 , 010 间接锁定 , 100 登录锁定 ;
     * 通过二进制转换为十进制入库(间接锁定和登录锁定可以同时存在): 0 正常，1 完全锁定，2 间接锁定, 4 登录锁定  ,6间接锁定+登录锁定
     */
    private Integer status;


    private Date registerDate;

    private Date lastUpdate;

    /**
     * 注册域名
     */
    private String registerDomain;

    /**
     * 注册客户端：0PC，1H5，2安卓，3IOS，4PAD
     */
    private Integer clientType;
    /**
     * 电话区号
     */
    private String telArea;

    /**
     * 是否虚拟
     */
    private String isFake;

    /**
     * 如果本用户是虚拟用户，那么本字段有效（本字段是多值拼接，例如1,2,3）
     * 1：AG
     * 2：EBET
     * 8：LB
     * 4：PT转账
     * 7：体育转账
     * 10：欢乐棋牌
     */
    private String unsupportPlatform;

    /**
     * 虚拟用户设置人
     */
    private String fakeCreator;

    /**
     * 虚拟用户设置时间
     */
    private Date fakeCreateTime;

    /**
     * 虚拟用户最后修改人
     */
    private String fakeLastModifier;

    /**
     * 虚拟用户最后修改时间
     */
    private Date fakeLastModifyTime;

    /**
     * 虚拟用户备注信息
     */
    private String fakeRemark;

//    /**
//     * 上次登录IP
//     */
//    @Transient
//    private String loginIp;
//
//    /**
//     * 上次登录时间
//     */
//    @Transient
//    private Date loginTime;
//
//    /**
//     * 玩家层级ID
//     */
//    @Transient
//    private Integer levelId;
//
//    /**
//     * 玩家层级
//     */
//    @Transient
//    private String levelName;
//
//    /**
//     * 玩家层级锁定状态
//     */
//    @Transient
//    private Integer levelStatus;
//
//    /**
//     * 用户账户余额
//     */
//    @Transient
//    private BigDecimal balance;
//
//    /**
//     * 用户需求流水
//     */
//    @Transient
//    private BigDecimal freezeBalance;
//
//    /**
//     * 用户有效流水
//     */
//    @Transient
//    private BigDecimal validBalance;
//
//    /**
//     * 剩余流水
//     */
//    @Transient
//    private BigDecimal leftAmount;
//
//    /**
//     * 代理返佣模式名称
//     */
//    @Transient
//    private String rebateName;
//
//    /**
//     * 上分状态：0关闭，1开启
//     */
//    @Transient
//    private Integer creditStatus;
//
//    /**
//     * 额度下发状态：0关闭，1开启
//     */
//    @Transient
//    private Integer proxyStatus;
//
//    /**
//     * 上分额度
//     */
//    @Transient
//    private BigDecimal creditAmount;
//
//    /**
//     * 对会员已上分额度
//     */
//    @Transient
//    private BigDecimal payOutAmount;
//
//    /**
//     * 对代理已分配额度
//     */
//    @Transient
//    private BigDecimal proxyCreditedAmount;
//
//    /**
//     * 可用的分配总额度
//     */
//    @Transient
//    private BigDecimal validAllocateAmount;
//
//    /**
//     * 登出时间
//     */
//    @Transient
//    private Date logoutTime;
//
//    /**
//     * 是否在线：true在线，false不在线
//     */
//    @Transient
//    private Boolean online;
//
//    /**
//     * 登录token
//     */
//    @Transient
//    private String token;
//
//    /**
//     * 收货地址姓名
//     */
//    @Transient
//    private String addressName;
//
//    /**
//     * 收货地址手机号
//     */
//    @Transient
//    private String addressMobile;
//
//    /**
//     * 收货地址省份
//     */
//    @Transient
//    private String addressProvince;
//
//    /**
//     * 收货地址城市
//     */
//    @Transient
//    private String addressCity;
//
//    /**
//     * 收货详细地址
//     */
//    @Transient
//    private String addressStreet;
//    /**
//     * 收货详细地址
//     */
//    @Transient
//    private String addressArea;
//
//    /**
//     * 手续费方案
//     */
//    @Transient
//    private Integer feeRuleId;
//
//    /**
//     * 手续费方案下月
//     */
//    @Transient
//    private Integer feeRuleNextId;
//
//    /**
//     * 佣金模式
//     */
//    @Transient
//    private Integer commMode;
//
//    /**
//     * 现金网方案
//     */
//    @Transient
//    private Integer cashRuleId;
//
//    /**
//     * 代理充值开关
//     */
//    @Transient
//    private boolean rechargeSwitch;
//
//    /**
//     * 注册IP 所属地
//     */
//    @Transient
//    private String registerIpAddress;
//
//    /**
//     * 登录IP 所属地
//     */
//    @Transient
//    private String loginIpAddress;
//
//    /**
//     * 用户VIP等级
//     */
//    @Transient
//    private Integer vipLevel;
//
//    /**
//     * 用户VIP等级状态，锁定状态，0：未锁定，1：已锁定
//     */
//    @Transient
//    private Integer lockStatus;
//
//    /**
//     * 用户是否有未审核的记录，0：没有，1：有(不可编辑)
//     */
//    @Transient
//    private Integer unauditedLockStatus;


}