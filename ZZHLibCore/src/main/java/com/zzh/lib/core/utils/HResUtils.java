package com.zzh.lib.core.utils;

import android.content.res.TypedArray;

import com.zzh.lib.core.HLibrary;

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
     * 获取手机status bar的高度
     *
     * @return status bar的高
     */
    public static int getStatusBarHeight() {
        int height = 0;
        int resourceId = HLibrary.getInstance().getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = HLibrary.getInstance().getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 获取ActionBar的高度
     *
     * @return ActionBar的高度
     */
    public static int getActionBarHeight() {
        TypedArray actionbarSizeTypedArray = HLibrary.getInstance().getContext().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        float actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0);
        return (int) actionBarHeight;
    }
}
