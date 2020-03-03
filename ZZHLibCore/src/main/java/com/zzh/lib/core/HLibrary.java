package com.zzh.lib.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zzh.lib.core.listener.HAppBackgroundListener;
import com.zzh.lib.manager.HAppManager;

public class HLibrary {
    private static HLibrary sInstance;
    private Context mContext;

    private static boolean isDebug;

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        HLibrary.isDebug = isDebug;
    }

    private HLibrary() {
    }

    public static HLibrary getInstance() {
        if (sInstance == null) {
            synchronized (HLibrary.class) {
                if (sInstance == null)
                    sInstance = new HLibrary();
            }
        }
        return sInstance;
    }

    public Context getContext() {
        return mContext;
    }


    public synchronized void init(Application app) {
        init(app, null);
    }

    public synchronized void init(Application application, HAppBackgroundListener.Callback callback) {
        if (application == null) {
            throw new NullPointerException("application is null, please check invoke init method");
        }

        if (mContext == null) {
            mContext = application;
            HAppManager.getInstance().init(application);
            HAppBackgroundListener.getInstance().init(application).addCallback(callback);
        }
    }

    /**
     * 获取最后一个activity
     *
     * @return
     */
    public static Activity getLastActivity() {
        return HAppManager.getInstance().getLastActivity();
    }
}
