package com.zzh.lib.core.utils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import com.zzh.lib.core.HLibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ZZH on 2020/8/5.
 *
 * @Date: 2020/8/5
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 系统设置。跳转到系统界面。
 */
public class HSystemUtils {
    /**
     * 跳转到app详情界面
     */
    public static void startAppDetailSettings() {
        String packageName = HLibrary.getInstance().getContext().getPackageName();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.fromParts("package", packageName, null));
        HLibrary.getInstance().getContext().startActivity(intent);
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            LogUtils.e("Unable to read sysprop " + propName + ", " + ex.getMessage());
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LogUtils.e("Exception while closing InputStream " + e.getMessage());
                }
            }
        }
        return line;
    }

    private static boolean isIntentAvailable(Intent intent) {
        if (intent == null) {
            return false;
        }
        return HLibrary.getInstance().getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}
