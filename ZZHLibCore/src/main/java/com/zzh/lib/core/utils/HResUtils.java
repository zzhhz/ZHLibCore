package com.zzh.lib.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.zzh.lib.core.HLibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator.
 *
 * @date: 2019/10/16
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ZHAutoSize.git
 * @since 1.0
 */
public class HResUtils {

    public static final String XIAOMI_FULLSCREEN_GESTURE = "force_fsg_nav_bar";
    public static final String XIAOMI_BRAND = "Xiaomi";
    /**
     * 获取字符串资源
     *
     * @param resId 字符串资源的ids
     * @return 字符串
     */
    public static String getString(int resId) {
        return HLibrary.getLastActivity().getString(resId);
    }

    /**
     * 获取字符串资源
     *
     * @param resId 资源ids
     * @param args  参数
     * @return 字符串
     */
    public static String getString(int resId, Object... args) {
        return HLibrary.getLastActivity().getString(resId, args);
    }

    /**
     * 获取资源文件中的颜色
     *
     * @param colorRes 颜色ID
     * @return 颜色值
     */
    public static int getIntColor(int colorRes) {
        return HLibrary.getLastActivity().getResources().getColor(colorRes);
    }

    public static float getDimension(int resId) {
        return HLibrary.getLastActivity().getResources().getDimension(resId);
    }

    public static int getDimensionPixelOffset(int resId) {
        return HLibrary.getLastActivity().getResources().getDimensionPixelOffset(resId);
    }

    public static int getDimensionPixelSize(int resId) {
        return HLibrary.getLastActivity().getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取资源id
     *
     * @param name       资源的名称
     * @param defType    类型：比如mipmap,drawable
     * @param defPackage 包名
     * @return 本资源指向的id
     */
    public static int getIdentifier(String name, String defType, String defPackage) {
        try {
            return HLibrary.getLastActivity().getResources().getIdentifier(name, defType, defPackage);
        } catch (Exception var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取drawable 文件夹下的资源
     *
     * @param name 资源的名称。默认获取drawable文件夹下的文件
     * @return 本资源指向的id
     */
    public static int getIdentifierDrawable(String name) {
        return getIdentifier(name, "drawable", HLibrary.getInstance().getContext().getPackageName());
    }

    /**
     * @return status bar的高
     * @see HResUtils#getStatusBarHeight(Context)
     */
    public static int getStatusBarHeight() {
        return getStatusBarHeight(HLibrary.getInstance().getContext());
    }

    /**
     * 获取手机status bar的高度
     *
     * @param ctx 当前上下文
     * @return status bar的高
     * @see HResUtils#getStatusBarHeight
     */
    public static int getStatusBarHeight(Context ctx) {
        int height = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = HLibrary.getInstance().getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * @return ActionBar的高度
     * @see HResUtils#getActionBarHeight(Context)
     */
    public static int getActionBarHeight() {
        return getActionBarHeight(HLibrary.getInstance().getContext());
    }

    /**
     * 获取ActionBar的高度
     *
     * @param ctx 上下文变量
     * @return ActionBar的高度
     * @see HResUtils#getActionBarHeight()
     */
    public static int getActionBarHeight(Context ctx) {
        TypedArray actionbarSizeTypedArray = ctx.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        float actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0);
        return (int) actionBarHeight;
    }

    /**
     * 获取底部导航栏高度
     *
     * @param activity 当前页面
     * @return Nav Bar 的高度
     */
    public static int getNavBarHeight(Activity activity) {
        int navigationBarHeight = 0;
        Resources resources = HLibrary.getInstance().getContext().getResources();
        int resourceId = resources.getIdentifier(isPortrait(activity) ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
        if (resourceId > 0 && checkDeviceHasNavBar(activity) && isNavigationBarVisible(activity)) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    /**
     * @return Nav Bar 的高度
     * @see HResUtils#getNavBarHeight(Activity)
     */
    public static int getNavBarHeight() {
        return getNavBarHeight(HLibrary.getLastActivity());
    }

    /**
     * 手机具有底部导航栏时，底部导航栏是否可见
     * 在小米手机上小米8、小米MIX系列全面屏无效，隐藏虚拟按键仍旧返回true
     * @param activity 判断导航栏是否可见
     * @return true 可见
     */
    public static boolean isNavigationBarVisible(Activity activity) {

        if (XIAOMI_BRAND.equalsIgnoreCase(HDeviceUtils.getDeviceBrand())) {
            int val = Settings.Global.getInt(activity.getContentResolver(), XIAOMI_FULLSCREEN_GESTURE, 0);
            return val != 0;
        } else {

            boolean show = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
                Point point = new Point();
                display.getRealSize(point);
                View decorView = activity.getWindow().getDecorView();
                Configuration conf = activity.getResources().getConfiguration();
                if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
                    View contentView = decorView.findViewById(android.R.id.content);
                    if (contentView != null) {
                        show = (point.x != contentView.getWidth());
                    }
                } else {
                    Rect rect = new Rect();
                    decorView.getWindowVisibleDisplayFrame(rect);
                    show = (rect.bottom != (point.y - getStatusBarHeight(activity)));
                    LogUtils.e(String.valueOf(point.y));
                    LogUtils.e(String.valueOf(rect.bottom));
                }
            }
            return show;
        }
    }

    /**
     * @see HResUtils#isNavigationBarVisible(Activity)
     */
    public static boolean isNavigationBarVisible() {
        return isNavigationBarVisible(HLibrary.getLastActivity());
    }

    /**
     * 检测是否具有底部导航栏
     *
     * @param activity 当前activity
     * @return true 具有底部导航栏
     */
    public static boolean checkDeviceHasNavBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager windowManager = activity.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            display.getRealMetrics(realDisplayMetrics);
            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;
            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        } else {
            boolean hasNavigationBar = false;
            Resources resources = Resources.getSystem();
            int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = resources.getBoolean(id);
            }
            try {
                Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
                Method m = systemPropertiesClass.getMethod("get", String.class);
                String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
                if ("1".equals(navBarOverride)) {
                    hasNavigationBar = false;
                } else if ("0".equals(navBarOverride)) {
                    hasNavigationBar = true;
                }
            } catch (Exception e) {
            }
            return hasNavigationBar;
        }
    }

    /**
     * @return
     * @see HResUtils#checkDeviceHasNavBar(Activity)
     */
    public static boolean checkDeviceHasNavBar() {
        return checkDeviceHasNavBar(HLibrary.getLastActivity());
    }

    /**
     * 是否为竖屏
     */
    public static boolean isPortrait(Context activity) {
        return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


    /**
     * @see HResUtils#getDisplayWidth(Context)
     */
    public static int getDisplayWidth() {
        return getDisplayWidth(HLibrary.getInstance().getContext());
    }

    /**
     * 获取屏幕的宽度
     *
     * @return 屏幕宽度
     */
    public static int getDisplayWidth(Context ctx) {
        DisplayMetrics dm = ctx.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * @see HResUtils#getDisplayHeight(Context)
     */
    public static int getDisplayHeight() {
        return getDisplayHeight(HLibrary.getInstance().getContext());//获取宽度
    }

    /**
     * 获取手机屏幕真实的高度。一般获取手机屏幕的高度是真实手机长度减去状态栏的高度。
     * getRealMetrics 这个方法是API 17之后才有的，之前的使用@hide 来标记的，并不对外开放API，
     * 所以API 小于 17使用反射的方法取得屏幕的真实高度
     *
     * @param ctx 上下文
     * @return 屏幕高度
     */
    public static int getDisplayHeight(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealMetrics(dm);
        } else {
            try {
                Class<?> clz = Class.forName("android.view.Display");
                Method method = clz.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(wm.getDefaultDisplay(), dm);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return dm.heightPixels;//获取宽度
    }

    /**
     * @param px 像素
     * @return 返回值单位是dp
     */
    public static int px2dp(float px) {
        float scale = HLibrary.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * @param dp dp值
     * @return 返回像素值
     */
    public static int dp2px(float dp) {
        float scale = HLibrary.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * @param px 像素
     * @return 返回值单位是dp
     */
    public static int px2sp(float px) {
        final float fontScale = HLibrary.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * @param sp 字体大小
     * @return 返回字体像素值
     */
    public static int sp2px(float sp) {
        final float fontScale = HLibrary.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (fontScale * sp + 0.5);
    }
}
