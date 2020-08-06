package com.zzh.core;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zzh.lib.core.HLibrary;
import com.zzh.lib.core.utils.HDeviceUtils;
import com.zzh.lib.core.utils.LogUtils;

/**
 * @Date: 2020/7/17 15:59
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 测试
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HLibrary.getInstance().init(this.getApplication());
        HLibrary.setDebug(true);
        LogUtils.e(HDeviceUtils.getDeviceBrand());
//        LogUtils.e(HDeviceUtils.getDeviceProduct());
//        LogUtils.e(HDeviceUtils.getDeviceBoard());
//        LogUtils.e(HDeviceUtils.getDeviceDevice());
//        LogUtils.e(HDeviceUtils.getDeviceManufacturer());
//        LogUtils.e(HDeviceUtils.getDeviceUser());

        /*boolean navigationBarVisible = HResUtils.isNavigationBarVisible(this);
        Log.e("-----", "--------nav bar 是否可见--------" + navigationBarVisible + ",\t\n -------has-" + HResUtils.checkDeviceHasNavBar(this));
        Log.e("-----", "\t\nnav bar: " + HResUtils.getNavBarHeight(this)
                + ", \t\nstatus bar: " + HResUtils.getStatusBarHeight(this)
                + ", \t\naction bar: " + HResUtils.getActionBarHeight(this)
                + ", \t\nscreen height: " + HResUtils.getDisplayHeight(this));*/
    }

    public void onClickView(View view) {
        //HSystemUtils.startAppPermissionSettings();
        LogUtils.e(HDeviceUtils.getDeviceBrand());
    }
}
