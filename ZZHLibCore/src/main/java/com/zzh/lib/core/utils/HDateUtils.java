package com.zzh.lib.core.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by ZZH on 2020-01-07.
 *
 * @Date: 2020-01-07
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 日期格式化
 */
public class HDateUtils {
    //高版本的SDK缓存
    private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();
    //低版本的日期格式化缓存
    private static final Map<String, SimpleDateFormat> LOW_LEVEL_FORMATTER_CACHE = new ConcurrentHashMap<>();

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 中文日期格式
     */
    public static final String DF_ZH_YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日 HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     */
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：yyyy/MM/dd
     */
    public static final String DF_YYYY_MM_DD_SEC = "yyyy/MM/dd";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm
     */
    public static final String DF_HH_MM = "HH:mm";

    public final static long MINUTE = 60 * 1000;// 1分钟
    public final static long HOUR = 60 * MINUTE;// 1小时
    public final static long DAY = 24 * HOUR;// 1天
    public final static long MONTH = 31 * DAY;// 月
    public final static long YEAR = 12 * MONTH;// 年
    public final static long FOUR_HOUR = 14400000;


    /**
     * @param date    日期字符串
     * @param pattern 日期字符串格式
     * @return 返回格式化的日期
     */
    public static Date parse(String date, String pattern) throws ParseException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = createCacheFormatter(pattern);
            return Date.from(LocalDateTime.parse(date, formatter).toInstant(ZoneOffset.from(OffsetDateTime.now())));
        } else {
            return createCacheLowLevelFormatter(pattern).parse(date);
        }
    }


    /**
     * 日期格式化
     *
     * @param localDate 时间
     * @param format    样式
     * @return 格式化后的字符串
     */
    public static String format(TemporalAccessor localDate, String format) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = createCacheFormatter(format);
            return formatter.format(localDate);
        } else {
            SimpleDateFormat cacheLowLevelFormatter = createCacheLowLevelFormatter(format);
            return cacheLowLevelFormatter.format(localDate);
        }
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式化样式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDate localDate = LocalDateTime.ofInstant(instant, zone).toLocalDate();
            return format(localDate, pattern);
        } else {
            SimpleDateFormat cacheLowLevelFormatter = createCacheLowLevelFormatter(pattern);
            return cacheLowLevelFormatter.format(date);
        }
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式化样式
     * @return 格式化后的字符串
     */
    public static String format(long date, String pattern) {
        return format(new Date(date), pattern);
    }

    /**
     * DateTimeFormatter缓存处理
     *
     * @param pattern 格式化后的样式
     * @return DateTimeFormatter实例
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static DateTimeFormatter createCacheFormatter(String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("-------格式化pattern为空----");
        }

        DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
        if (formatter == null) {
            synchronized (HDateUtils.class) {
                formatter = FORMATTER_CACHE.get(pattern);
                if (null == formatter) {
                    formatter = DateTimeFormatter.ofPattern(pattern);
                    FORMATTER_CACHE.put(pattern, formatter);
                }
            }
        }
        return formatter;
    }

    /**
     * 创建O之下的版本，日期格式化。
     *
     * @param pattern 格式化后的样式
     * @return SimpleDateFormat实例
     */
    public static SimpleDateFormat createCacheLowLevelFormatter(String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("-------格式化pattern为空----");
        }
        SimpleDateFormat dateFormat = LOW_LEVEL_FORMATTER_CACHE.get(pattern);
        if (dateFormat == null) {
            synchronized (HDateUtils.class) {
                dateFormat = LOW_LEVEL_FORMATTER_CACHE.get(pattern);
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    LOW_LEVEL_FORMATTER_CACHE.put(pattern, dateFormat);
                }
            }
        }
        return dateFormat;
    }
}
