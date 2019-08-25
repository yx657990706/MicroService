package com.yx.appcoreservicer.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2019/1/10
 * @time 10:12 AM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class NumberUtil {

    /**
     * 获取6位验证码
     *
     * @return
     */
    public static String getVerifyCode() {
        return new Random().nextInt(899999) + 100000+"";
    }

    /**
     * 获取6位验证码
     *
     * @return
     */
    public static String getVerifyCode2() {
        return randomString(6);
    }

    /**
     * 获取指定位数的的string随机数，随机范围为a-z A-Z 0-9
     *
     * @param length string的长度
     * @return 指定lenght的随机字符串
     */
    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();

    }

    /**
     * 获取指定位数的随机数串，随机范围为0-9
     *
     * @param length string的长度
     * @return 指定lenght的随机字符串
     */
    public static String randomNumStr(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(10);
            buf.append(str.charAt(num));
        }
        return buf.toString();

    }

    /**
     * 获取指定位数的随机密码，随机范围为a-z A-Z 0-9 + 特殊字符
     *
     * @param length
     * @return
     */
    public static String randomPassword(int length) {
        String str = "I?DzB/jTmZf|P>ru`p%_v79EJ-=oG+[kx!Hwt)O5;.WsU2(*c{l~}gX0C$N:QFb^YS&,aM6VKin@]y#8'R4d3LqAe1<h";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(92);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    /**
     * 保留2位小数
     * @param num
     * @return
     */
    private static String getTwoDecimalNumber(Double num) {
        //方式1：转换方便（#.00会在小于1时丢失整数位的0）
        DecimalFormat df = new DecimalFormat("0.00");

        //方式2：打印方便
//        System.out.println("2===>>"+String.format("%.2f", num));

        //方式3：BigDecimal可以控制精度
//        BigDecimal bg = new BigDecimal(num);
//        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df.format(num);
    }

    public static void main(String[] args) {
        getTwoDecimalNumber(1234.0);
        System.out.println("密码===>>"+randomPassword(6));
        System.out.println("随机数===>>"+randomString(6));
    }
}
