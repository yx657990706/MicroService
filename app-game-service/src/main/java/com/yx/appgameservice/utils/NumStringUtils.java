package com.yx.appgameservice.utils;


import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumStringUtils {

    private static final String upperCaseChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowerCaseChar = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final String fixedLowerCaseChar = "23456789abcdefghijkmnpqrstuvwxyz";
    private static String[] units =
            {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿"};
    private static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    /**
     * 数字转汉字
     *
     * @param num
     * @return
     */
    public static String numToChinese(int num) {
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            if (val[i] - 48 == 0) {
                if (sb.length() == 0 || sb.lastIndexOf("零") != sb.length() - 1) {
                    sb.append(numArray[0]);
                }
            } else {
                sb.append(numArray[val[i] - 48]);
                sb.append(units[len - i - 1]);
            }
        }
        if (sb.indexOf("一十") == 0) {
            sb.deleteCharAt(0);
        }
        if (sb.length() > 1 && sb.indexOf("零") == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 将数字转换成6位小写字符串
     *
     * @param num
     * @return
     */
    public static String numToSixLowerString(long num) {
        return numToLowerString(num, 6);
    }

    public static String numToFixedLowerString(long num) {
        return numToFixedLowerString(num, 6);
    }

    /**
     * 将数字转换成小写字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String numToLowerString(long num, int len) {
        StringBuilder sb = new StringBuilder();
        Assert.state(num > 0);
        while (num > 0) {
            sb.insert(0, lowerCaseChar.charAt((int) (num % lowerCaseChar.length())));
            num = num / lowerCaseChar.length();
        }
        int length = sb.length();
        for (int i = 0; i < len - length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static String numToFixedLowerString(long num, int len) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, fixedLowerCaseChar.charAt((int) (num % fixedLowerCaseChar.length())));
            num = num / fixedLowerCaseChar.length();
        }
        int length = sb.length();
        for (int i = 0; i < len - length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    /**
     * numToLowerString 的反函数
     */
    public static long lowerStringToNum(String lowerString) {

        long total = 0L;
        for (char c : lowerString.toCharArray()) {
            int charIndex = lowerCaseChar.indexOf(c);
            if (lowerCaseChar.indexOf(c) == -1) {
                throw new IllegalArgumentException("不合法字串: " + lowerString);
            }

            total *= lowerCaseChar.length();
            total += charIndex;
        }

        return total;
    }

    /**
     * 将数字转换成6位大写字符串
     *
     * @param num
     * @return
     */
    public static String numToSixUpperString(long num) {
        return numToUpperString(num, 6);
    }

    /**
     * 将数字转换成大写字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String numToUpperString(long num, int len) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, upperCaseChar.charAt((int) (num % upperCaseChar.length())));
            num = num / upperCaseChar.length();
        }
        int length = sb.length();
        for (int i = 0; i < len - length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    /**
     * 生成期数
     *
     * @param planId
     * @param maxNum
     * @return
     */
    public static String numToPlanId(String ticketType, Integer planId, Integer maxNum) {
        StringBuffer periodFormat = new StringBuffer();
        if ("dp".equals(ticketType)) {
            return periodFormat.append("-").append(planId).toString();
        } else {
            if (maxNum - planId.toString().length() == 2) {
                return periodFormat.append("-00").append(planId).toString();
            } else if (maxNum - planId.toString().length() == 1) {
                return periodFormat.append("-0").append(planId).toString();
            } else {
                return periodFormat.append("-").append(planId).toString();
            }
        }
    }

    /**
     * 获取修正后的字符
     *
     * @param str
     * @param len
     * @return
     */
    public static String getFixedLowerString(String str, int len) {
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        while (sb.length() < len && idx < str.length()) {
            if (fixedLowerCaseChar.contains(str.charAt(idx) + "")) {
                sb.append(str.charAt(idx));
            }
            idx++;
        }
        int length = sb.length();
        for (int i = 0; i < len - length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static String simpleEncrypt(String str, int from, int end) {
        int len = str.length();
        if (len <= from) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, from));
        if (len <= end) {
            for (int i = 0; i < len - from; i++) {
                sb.append("*");
            }
        } else {
            for (int i = 0; i < end - from; i++) {
                sb.append("*");
            }
            sb.append(str.substring(end, str.length()));
        }
        return sb.toString();
    }

    public static String simpleEncrypt(String str, int len) {
        return simpleEncrypt(str, len, str.length());
    }

    public static List<Long> splitNum(long total, long min, int count) {
        List<Long> result = new ArrayList<>();
        long randomTotal = total - min * count;
        for (int i = 0; i < count; i++) {
            if (i != count - 1) {
                long random = (long) (Math.random() * randomTotal);
                if (random > total / 2) {
                    random = (long) (random * 0.6);
                }
                result.add(random + min);
                randomTotal = randomTotal - random;
            } else {
                result.add(randomTotal + min);
            }
        }
        return result;
    }

    /**
     * 判断字符串是否全部为中文字符组成
     *
     * @param str 检测的文字
     * @return true：为中文字符串，false:含有非中文字符
     */
    public static boolean isChineseStr(String str) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(c[i]));
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public static String random(int len) {
        StringBuilder res = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            int idx = r.nextInt(fixedLowerCaseChar.length());
            res.append(String.valueOf(fixedLowerCaseChar.charAt(idx)));
        }
        return res.toString();
    }


    /**
     * 保留两位小数,3位及以后数字大于零的;两位小数直接进一: 123.123 处理结果为123.13
     *
     * @param decimal
     * @return
     */
    public static String formatDecimal(BigDecimal decimal) {
        if (ObjectUtils.isEmpty(decimal)) {
            return null;
        }
        return decimal.setScale(2, BigDecimal.ROUND_UP).toString();
    }
}