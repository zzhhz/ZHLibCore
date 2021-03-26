package com.zzh.lib.core.utils;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZZH on 3/19/21.
 *
 * @Date: 3/19/21
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 定时任务。单例模式
 */
public class HTaskUtils {

    /**
     * 核心线程数量
     */
    public static final int corePoolSize = 8;

    private static HTaskUtils mHTaskUtils;
    private static ScheduledExecutorService mScheduledExecutorService;

    private HTaskUtils() {
    }

    public static HTaskUtils getInstance() {

        if (mHTaskUtils == null) {
            synchronized (HTaskUtils.class) {
                if (mHTaskUtils == null) {
                    initScheduledExecutorService();
                    return mHTaskUtils = new HTaskUtils();
                }
            }
        }


        return mHTaskUtils;
    }

    private static void initScheduledExecutorService() {
        if (mScheduledExecutorService == null) {
            mScheduledExecutorService = new ScheduledThreadPoolExecutor(corePoolSize);
        }
    }


    /**
     * 延迟执行任务
     *
     * @param task 执行任务
     * @param ms   延迟毫秒值
     */
    public void execute(Runnable task, long ms) {
        if (mScheduledExecutorService == null) {
            initScheduledExecutorService();
        }
        mScheduledExecutorService.schedule(task, ms, TimeUnit.MILLISECONDS);
    }

    /**
     * 延迟执行任务
     *
     * @param task  任务
     * @param delay 延迟执行时间
     * @param unit  延迟执行时间单位
     */
    public void execute(Runnable task, long delay, TimeUnit unit) {
        if (mScheduledExecutorService == null) {
            initScheduledExecutorService();
        }
        mScheduledExecutorService.schedule(task, delay, unit);
    }
}
