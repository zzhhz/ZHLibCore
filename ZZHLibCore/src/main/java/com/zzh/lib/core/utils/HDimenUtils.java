package com.zzh.lib.core.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.zzh.lib.core.HLibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZZH on 2020-02-08.
 *
 * @Date: 2020-02-08
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class HDimenUtils {

    /**
     * 判断手机号是否合法
     *
     * @param mobiles 手机号
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((19[0-9])|(14[5-7])|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 获取屏幕的宽度
     *
     * @return 屏幕宽度
     */
    public static int getDisplayWidth() {
        DisplayMetrics dm = HLibrary.getInstance().getContext().getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取手机屏幕真实的高度。一般获取手机屏幕的高度是真实手机长度减去状态栏的高度。
     * getRealMetrics 这个方法是API 17之后才有的，之前的使用@hide 来标记的，并不对外开放API，
     * 所以API 小于 17使用反射的方法取得屏幕的真实高度
     *
     * @return 屏幕高度
     */
    public static int getDisplayHeight() {
        WindowManager wm = (WindowManager) HLibrary.getInstance().getContext().getSystemService(Context.WINDOW_SERVICE);
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
     * @param px  像素
     * @return 返回值单位是dp
     */
    public static int px2dp(float px) {
        float scale = HLibrary.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * @param dp  dp值
     * @return 返回像素值
     */
    public static int dp2px(float dp) {
        float scale = HLibrary.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * @param px  像素
     * @return 返回值单位是dp
     */
    public static int px2sp(float px) {
        final float fontScale = HLibrary.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * @param sp  字体大小
     * @return 返回字体像素值
     */
    public static int sp2px(float sp) {
        final float fontScale = HLibrary.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (fontScale * sp + 0.5);
    }


}
