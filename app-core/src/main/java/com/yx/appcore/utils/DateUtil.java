package com.yx.appcore.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期处理工具类(基于joda-time)
 *
 * @author leo
 */
public class DateUtil {

    private static final String ISO_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
    private static final String ISO_DATE = "yyyy-MM-dd";
    private static final String MY_TIMESTAMP = "yyyyMMddHHmmss";
    private static final String MY_DATE = "yyyyMMdd";

    private static final int SECOND = 1;//秒
    private static final int MINUTE = 2;//分
    private static final int HOURS = 3;//时
    private static final int DAY = 4;//天
    private static final int WEEK = 5;//周
    private static final int MONTH = 6;//月
    private static final int YEAR = 7;//年

    public DateUtil() {
    }

    /**
     * 获取当前系统的时间（毫秒级）
     *
     * @return
     */
    public static long getCurrentSecondMillis() {
        return DateTimeUtils.currentTimeMillis();
    }

    /**
     * 获取当天的开始时间
     *
     * @return
     */
    public static String getStartOfDay() {
        DateTime dateTime = new DateTime(new Date());
        DateTime startOfDay = dateTime.withTimeAtStartOfDay();
        return startOfDay.toString(ISO_TIMESTAMP);
    }

    /**
     * 获取当天的结束时间
     *
     * @return
     */
    public static String getEndOfDay() {
        DateTime dateTime = new DateTime(new Date());
        DateTime endOfDay = dateTime.millisOfDay().withMaximumValue();
        return endOfDay.toString(ISO_TIMESTAMP);
    }

    /**
     * 获取某天的开始时间<br>
     * yyyy-MM-dd 00:00:00
     *
     * @param strDate
     * @return
     */
    public static String getStartOfDay(String strDate) throws ParseException {
        DateTime dateTime = getJodaDateTime(strDate);
        DateTime startOfDay = dateTime.withTimeAtStartOfDay();
        return startOfDay.toString(ISO_TIMESTAMP);
    }

    /**
     * 获取某天的结束时间
     *
     * @param strDate
     * @return
     */
    public static String getEndOfDay(String strDate) throws ParseException {
        DateTime dateTime = getJodaDateTime(strDate);
        DateTime endOfDay = dateTime.millisOfDay().withMaximumValue();
        return endOfDay.toString(ISO_TIMESTAMP);
    }

    /**
     * 按照指定格式取当前时间
     *
     * @param formatStr
     * @return
     */
    public static String getNowDate(String formatStr) {
        Assert.notNull(formatStr, "formatStr不能为空");
        DateTime nowTime = new DateTime();
        return nowTime.toString(formatStr);
    }

    /**
     * 获取当前时间<br>
     * 按照国际标准格式:yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTime() {
        return getNowDate(ISO_TIMESTAMP);
    }

    /**
     * 获取当前日期<br>
     * 按照国际标准格式:yyyy-MM-dd
     */
    public static String getNowDate() {
        return getNowDate(ISO_DATE);
    }

    /**
     * 获取DateTime
     *
     * @param strDate
     * @return
     */
    public static DateTime getJodaDateTime(String strDate) throws ParseException {
        DateTime dateTime = new DateTime(toDate(strDate));
        return dateTime;
    }

    /**
     * 获取DateTime（可指定格式）
     *
     * @param strDate
     * @param format
     * @return
     */
    public static DateTime getJodaDateTime(String strDate, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = formatter.parseDateTime(strDate);
        return dateTime;
    }

    /**
     * 将字符串日期转换为java日期
     *
     * @param dtStr
     * @return
     * @throws ParseException
     */
    public static Date toDate(String dtStr) throws ParseException {
        //自适应格式 根据格式的长度不一致的特点匹配
        //若是其他格式，请用扩展或使用带格式的转换
        if (ISO_TIMESTAMP.length() == dtStr.length()) {
            return toDate(dtStr, ISO_TIMESTAMP);
        } else if (MY_TIMESTAMP.length() == dtStr.length()) {
            return toDate(dtStr, MY_TIMESTAMP);
        } else if (MY_DATE.length() == dtStr.length()) {
            return toDate(dtStr, MY_DATE);
        } else {// ISO_DATE
            return toDate(dtStr, ISO_DATE);
        }
    }

    /**
     * 将字符串日期转换为java日期
     *
     * @param dtStr
     * @param pattern dtStr的格式
     * @return
     * @throws ParseException
     */
    public static Date toDate(String dtStr, String pattern) throws ParseException {
        Assert.notNull(dtStr, "字符串日期不能为空");
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = DateTime.parse(dtStr, format);
        return dateTime.toDate();
    }

    /**
     * 按照指定格式将日期转换为字符串格式
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String toStrDate(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    /**
     * 将指定日期转换为字符串格式(yyyy-MM-dd )
     *
     * @param dtStr
     * @return
     * @throws ParseException
     */
    public static String toStrDate(String dtStr) throws ParseException {
        return toStrDate(toDate(dtStr), ISO_DATE);
    }

    /**
     * 指定日期转换为字符串格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param dtStr
     * @return
     * @throws ParseException
     */
    public static String toStrTimeStamp(String dtStr) throws ParseException {
        return toStrDate(toDate(dtStr), ISO_TIMESTAMP);
    }

    /**
     * 指定日期转换为字符串格式<br>
     * 国际标准格式:yyyy-MM-dd HH:mm:ss
     *
     * @param dt
     * @return
     */
    public static String toStrDate(Date dt) {
        return toStrDate(dt, ISO_DATE);
    }

    /**
     * 指定时间增加n天后的时间<br>
     * yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 字符串日期
     * @param days    天数
     * @return
     */
    public static String addDays(String strDate, int days) throws ParseException {
        DateTime dateTime = getJodaDateTime(strDate);
        return dateTime.plusDays(days).toString(ISO_TIMESTAMP);
    }

    /**
     * 指定时间减少n天后的时间<br>
     *
     * @param strDate
     * @param days
     * @return
     */
    public static String minusDays(String strDate, int days) throws ParseException {
        DateTime dateTime = getJodaDateTime(strDate);
        return dateTime.minusDays(days).toString(ISO_TIMESTAMP);
    }

    /**
     * 指定时间增加n单位后的时间<br>
     * 若要减少时间，num传负数
     *
     * @param strDate 指定的时间
     * @param unit    单位（秒/分钟/小时/天/周/月/年）
     * @param num     数量
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String addTime(String strDate, int unit, int num) throws ParseException {
        DateTime dateTime = getJodaDateTime(strDate);
        String time = "";
        switch (unit) {
            case SECOND:
                time = dateTime.plusSeconds(num).toString(ISO_TIMESTAMP);
                break;
            case MINUTE:
                time = dateTime.plusMinutes(num).toString(ISO_TIMESTAMP);
                break;
            case HOURS:
                time = dateTime.plusHours(num).toString(ISO_TIMESTAMP);
                break;
            case DAY:
                time = dateTime.plusDays(num).toString(ISO_TIMESTAMP);
                break;
            case WEEK:
                time = dateTime.plusWeeks(num).toString(ISO_TIMESTAMP);
                break;
            case MONTH:
                time = dateTime.plusMonths(num).toString(ISO_TIMESTAMP);
                break;
            case YEAR:
                time = dateTime.plusYears(num).toString(ISO_TIMESTAMP);
                break;
            default:
        }
        return time;
    }

    /**
     * 获取日期是周几
     *
     * @param strDate
     * @return
     */
    public static String getDayDes(String strDate) throws ParseException {
        DateTime dateTime = getJodaDateTime(strDate);
        String day = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                day = "星期日";
                break;
            case DateTimeConstants.MONDAY:
                day = "星期一";
                break;
            case DateTimeConstants.TUESDAY:
                day = "星期二";
                break;
            case DateTimeConstants.WEDNESDAY:
                day = "星期三";
                break;
            case DateTimeConstants.THURSDAY:
                day = "星期四";
                break;
            case DateTimeConstants.FRIDAY:
                day = "星期五";
                break;
            case DateTimeConstants.SATURDAY:
                day = "星期六";
                break;
            default:
        }
        return day;
    }

    /**
     * 获取2个时间差
     *
     * @param start
     * @param end
     * @param unit  单位（秒/分钟/小时/天/周/月/年）
     * @return
     */
    public static int getTwoDateScope(String start, String end, int unit) throws ParseException {
        DateTime a = getJodaDateTime(start);
        DateTime b = getJodaDateTime(end);
        int scope = 0;
        switch (unit) {
            case SECOND:
                scope = new Period(a, b, PeriodType.seconds()).getSeconds();
                break;
            case MINUTE:
                scope = new Period(a, b, PeriodType.minutes()).getMinutes();
                break;
            case HOURS:
                scope = new Period(a, b, PeriodType.hours()).getHours();
                break;
            case DAY:
                scope = new Period(a, b, PeriodType.days()).getDays();
                break;
            case WEEK:
                scope = new Period(a, b, PeriodType.weeks()).getWeeks();
                break;
            case MONTH:
                scope = new Period(a, b, PeriodType.months()).getMonths();
                break;
            case YEAR:
                scope = new Period(a, b, PeriodType.yearDay()).getYears();
                break;
            default:
        }
        return scope;
    }

    /**
     * 时间比较before
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isBefore(String start, String end) throws ParseException {
        DateTime a = getJodaDateTime(start);
        DateTime b = getJodaDateTime(end);
        return a.isBefore(b);
    }

    /**
     * 时间比较before(和系统时间比)
     *
     * @param strDate
     * @return
     */
    public static boolean isBefore(String strDate) throws ParseException {
        DateTime a = getJodaDateTime(strDate);
        return a.isBeforeNow();
    }

    /**
     * 时间比较after
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isAfter(String start, String end) throws ParseException {
        DateTime a = getJodaDateTime(start);
        DateTime b = getJodaDateTime(end);
        return a.isAfter(b);
    }

    /**
     * 时间比较after(和系统时间比)
     *
     * @param strDate
     * @return
     */
    public static boolean isAfter(String strDate) throws ParseException {
        DateTime a = getJodaDateTime(strDate);
        return a.isAfterNow();
    }


    public static void main(String[] args) throws ParseException {
        String a = "2019-01-01 00:00:00";
        String b = "2019-01-01 23:59:59";
        System.out.println("秒:" + getTwoDateScope(a, b, DateUtil.SECOND));
        System.out.println("分:" + getTwoDateScope(a, b, DateUtil.MINUTE));
        System.out.println("时:" + getTwoDateScope(a, b, DateUtil.HOURS));
        System.out.println("天:" + getTwoDateScope(a, b, DateUtil.DAY));
        System.out.println("周:" + getTwoDateScope(a, b, DateUtil.WEEK));
        System.out.println("月:" + getTwoDateScope(a, b, DateUtil.MONTH));
        System.out.println("年:" + getTwoDateScope(a, b, DateUtil.YEAR));

        System.out.println("秒:" + addTime(a, DateUtil.SECOND, 59));
        System.out.println("分:" + addTime(a, DateUtil.MINUTE, 59));
        System.out.println("时:" + addTime(a, DateUtil.HOURS, 59));
        System.out.println("天:" + addTime(a, DateUtil.DAY, 9));
        System.out.println("周:" + addTime(a, DateUtil.WEEK, 2));
        System.out.println("月:" + addTime(a, DateUtil.MONTH, 1));
        System.out.println("年:" + addTime(a, DateUtil.YEAR, 5));

    }
}
