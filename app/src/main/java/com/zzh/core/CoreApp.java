package com.zzh.core;

import android.app.Application;

import com.zzh.lib.core.HLibrary;

/**
 * Created by ZZH on 2020/12/1.
 *
 * @Date: 2020/12/1
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class CoreApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HLibrary.getInstance().init(this);
        HLibrary.setDebug(true);
    }
}
