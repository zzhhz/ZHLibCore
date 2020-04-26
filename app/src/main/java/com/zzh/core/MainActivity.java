package com.zzh.core;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.zzh.lib.core.HLibrary;
import com.zzh.lib.core.utils.HDeviceUtils;
import com.zzh.lib.core.utils.HResUtils;
import com.zzh.lib.core.utils.LogUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HLibrary.getInstance().init(this.getApplication());
        HLibrary.setDebug(true);
        LogUtils.e(HDeviceUtils.getDeviceBrand());
        LogUtils.e(HDeviceUtils.getDeviceProduct());
        LogUtils.e(HDeviceUtils.getDeviceBoard());
        LogUtils.e(HDeviceUtils.getDeviceDevice());
        LogUtils.e(HDeviceUtils.getDeviceManufacturer());
        LogUtils.e(HDeviceUtils.getDeviceUser());

        boolean navigationBarVisible = HResUtils.isNavigationBarVisible(this);
        Log.e("-----", "--------nav bar 是否可见--------" + navigationBarVisible + ",\t\n -------has-"
                + HResUtils.checkDeviceHasNavBar(this));
        Log.e("-----", "\t\nnav bar: " + HResUtils.getNavBarHeight(this)
                + ", \t\nstatus bar: " + HResUtils.getStatusBarHeight(this)
                + ", \t\naction bar: " + HResUtils.getActionBarHeight(this));
    }
}
