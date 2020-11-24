package com.zzh.core;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.zzh.lib.core.HLibrary;
import com.zzh.lib.core.utils.HDeviceUtils;
import com.zzh.lib.core.utils.HFileUtils;
import com.zzh.lib.core.utils.HResUtils;
import com.zzh.lib.core.utils.LogUtils;

import java.io.File;

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
        LogUtils.e(HDeviceUtils.getDeviceProduct());
        LogUtils.e(HDeviceUtils.getDeviceBoard());
        LogUtils.e(HDeviceUtils.getDeviceDevice());
        LogUtils.e(HDeviceUtils.getDeviceManufacturer());
        LogUtils.e(HDeviceUtils.getDeviceUser());

        boolean navigationBarVisible = HResUtils.isNavigationBarVisible(this);
        /*Log.e("-----", "--------nav bar 是否可见--------" + navigationBarVisible + ",\t\n -------has-" + HResUtils.checkDeviceHasNavBar(this));
        Log.e("-----", "\t\nnav bar: " + HResUtils.getNavBarHeight(this)
                + ", \t\nstatus bar: " + HResUtils.getStatusBarHeight(this)
                + ", \t\naction bar: " + HResUtils.getActionBarHeight(this)
                + ", \t\nscreen height: " + HResUtils.getDisplayHeight(this));*/
        LogUtils.e("----disk cache dir: " + HFileUtils.getDiskCacheDir());
        String rootPath = HFileUtils.getSDCardRootPath();
        LogUtils.e("----disk root dir: " + rootPath);
        LogUtils.e("----disk root size: " + HFileUtils.getSDAllSize());


    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onClickView(View view) {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            boolean directory = HFileUtils.createSDCardDirectory(new File(HFileUtils.getSDCardRootPath(), "test-aaa").getAbsolutePath());
            LogUtils.e("----disk create dir: " + directory);
            boolean dirPackage = HFileUtils.createSDCardDirectory(new File(HFileUtils.getSDCardRootPath(), getPackageName()).getAbsolutePath());
            LogUtils.e("----disk create package dir: " + dirPackage);
            LogUtils.e("----disk create package: " + HFileUtils.createSDCardDirectory("/storage/emulated/0/Android/data/com.zzh.core/test"));
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

    }
}
