package com.zzh.lib.core.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

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

    /**
     * 跳转到app 权限设置界面
     */
    public static void startAppPermissionSettings() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (HResUtils.XIAOMI_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {
                intent = toXIAOMIAppPermission();
            } else if (HResUtils.MEIZU_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {
                intent = toMEIZUPermission();
            } else if (HResUtils.HUAWEI_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {
                intent = toHUAWEIPermission();
            } else if ("qiku".equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {
                intent = toQIKUPermission();
            } else if ("oppo".equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {
                intent = toOPPOPermission();
            } else {
                String packageName = HLibrary.getInstance().getContext().getPackageName();
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.fromParts("package", packageName, null));
            }
        } else {
            if (HResUtils.MEIZU_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {
                intent = toMEIZUPermission();
            } else {
                if ("oppo".equalsIgnoreCase(HDeviceUtils.getDeviceBrand())
                        || HResUtils.VIVO_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())
                        || HResUtils.HUAWEI_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())
                        || HResUtils.HUAWEI_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())
                        || HResUtils.HUAWEI_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {

                } else if (HResUtils.XIAOMI_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {

                }
            }

        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        HLibrary.getInstance().getContext().startActivity(intent);
    }

    private static Intent toOPPOPermission() {
        Intent intent = new Intent();
        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");//悬浮窗管理页面
            intent.setComponent(comp);
        } catch (Exception e) {
            String packageName = HLibrary.getInstance().getContext().getPackageName();
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromParts("package", packageName, null));
        }
        return intent;
    }

    private static Intent toQIKUPermission() {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(intent)) {
        } else {
            intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
            if (isIntentAvailable(intent)) {

            } else {
                LogUtils.e("can't open permission page with particular name, please use " +
                        "\"adb shell dumpsys activity\" command and tell me the name of the float window permission page");
            }
        }
        return intent;
    }

    /**
     * 华为设置界面
     *
     * @return
     */
    public static Intent toHUAWEIPermission() {
        Intent intent = new Intent();
        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
            intent.setComponent(comp);
            if (getEMUIVersion() == 3.1) {
                //emui 3.1 的适配
            } else {
                //emui 3.0 的适配
                comp = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");//悬浮窗管理页面
                intent.setComponent(comp);
            }
        } catch (SecurityException e) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//   ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            //华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
//      ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
            intent.setComponent(comp);
            LogUtils.e(Log.getStackTraceString(e));
        } catch (ActivityNotFoundException e) {
            /**
             * 手机管家版本较低 HUAWEI SC-UL10
             */
//   Toast.makeText(MainActivity.this, "act找不到", Toast.LENGTH_LONG).show();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem");//权限管理页面 android4.4
//   ComponentName comp = new ComponentName("com.android.settings","com.android.settings.permission.single_app_activity");//此处可跳转到指定app对应的权限管理页面，但是需要相关权限，未解决
            intent.setComponent(comp);
            LogUtils.e(Log.getStackTraceString(e));
        } catch (Exception e) {
            //抛出异常时提示信息
            LogUtils.e(Log.getStackTraceString(e));
            String packageName = HLibrary.getInstance().getContext().getPackageName();
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromParts("package", packageName, null));
        }
        return intent;
    }

    public static Intent toMEIZUPermission() {
        Intent intent = null;
        String packageName = HLibrary.getInstance().getContext().getPackageName();
        try {
            intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.putExtra("packageName", packageName);
        } catch (Exception e) {
            try {
                LogUtils.e("获取悬浮窗权限, 打开AppSecActivity失败, " + Log.getStackTraceString(e));
                // 最新的魅族flyme 6.2.5 用上述方法获取权限失败, 不过又可以用下述方法获取权限了
                intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + packageName));
            } catch (Exception eFinal) {
                LogUtils.e("获取悬浮窗权限失败, 通用获取方法失败, " + Log.getStackTraceString(eFinal));
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.fromParts("package", packageName, null));
            }
        }
        return intent;
    }


    static Intent toXIAOMIAppPermission() {
        int romVer = getMIUIVersion();
        String packageName = HLibrary.getInstance().getContext().getPackageName();
        Intent intent = null;
        if (romVer == 5) {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null));
        } else if (romVer == 6 || romVer == 7) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else if (romVer == 8 || romVer == 9) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else {
            intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("package:" + packageName));
        }
        return intent;
    }


    /**
     * 获取小米系统MIUI 版本号
     *
     * @return
     */
    public static int getMIUIVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name");
        if (version != null) {
            try {
                return Integer.parseInt(version.substring(1));
            } catch (Exception e) {
                LogUtils.e("get miui version code error, version : " + version);
                LogUtils.e(Log.getStackTraceString(e));
            }
        }
        return -1;
    }

    public static double getEMUIVersion() {
        try {
            String emuiVersion = getSystemProperty("ro.build.version.emui");
            String version = emuiVersion.substring(emuiVersion.indexOf("_") + 1);
            return Double.parseDouble(version);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return 4.0;
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
