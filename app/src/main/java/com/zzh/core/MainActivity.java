package com.zzh.core;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.zzh.lib.core.HLibrary;
import com.zzh.lib.core.utils.HDeviceUtils;
import com.zzh.lib.core.utils.HFileUtils;
import com.zzh.lib.core.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

        String absolutePath = HFileUtils.getDatabaseFile().getAbsolutePath();
        LogUtils.e(absolutePath);

        File file = new File(absolutePath, "aaa.log");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            HFileUtils.saveFile("测试测试数据库".getBytes("UTF-8"), file);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onClickView(View view) {


    }
}
