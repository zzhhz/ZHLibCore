package com.zzh.lib.core.utils;

import android.util.Log;

import com.zzh.lib.core.HLibrary;

/**
 * @date: 2019/9/5
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: LogUtil.java 日志输出
 */
public class LogUtils {
    private static final int ELEMENTS_INDEX = 1;
    static String className;
    static String methodName;
    static int lineNumber;

    private LogUtils() {
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[ELEMENTS_INDEX].getFileName();
        methodName = sElements[ELEMENTS_INDEX].getMethodName();
        lineNumber = sElements[ELEMENTS_INDEX].getLineNumber();
    }

    public static void e(String message) {
        if (!HLibrary.isDebug())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void i(String message) {
        if (!HLibrary.isDebug())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!HLibrary.isDebug())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!HLibrary.isDebug())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!HLibrary.isDebug())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!HLibrary.isDebug())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

    public static String build(String... content) {
        StringBuilder sb = new StringBuilder();
        if (content != null) {
            for (int i = 0; i < content.length; i++) {
                if ((i + 1) % 2 != 0) // 奇数
                {
                    sb.append("(").append(content[i]).append(":");
                } else {
                    sb.append(content[i]).append(") ");
                }
            }
        }
        return sb.toString();
    }
}
