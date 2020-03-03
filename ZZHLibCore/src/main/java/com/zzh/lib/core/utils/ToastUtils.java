package com.zzh.lib.core.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.zzh.lib.core.HLibrary;

/**
 * Created by Administrator.
 *
 * @date: 2019/8/15
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: 提示消息
 * @since 1.0
 */
public class ToastUtils {

    public static final Handler sHandler = new Handler(Looper.getMainLooper());

    private static Toast sToast;


    /**
     * 短提示
     *
     * @param text 提示文字
     */
    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }


    /**
     * @param msg      提示的文字
     * @param duration 提示长短
     */
    public static void show(CharSequence msg, int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showInternal(msg, duration);
        } else {
            sHandler.post(() -> {
                showInternal(msg, duration);
            });
        }
    }

    /**
     * 提示信息
     *
     * @param text     提示文字
     * @param duration 长短提示
     *                 提示文字显示在屏幕中心
     */
    private static void showInternal(CharSequence text, int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (sToast != null) {
            sToast.setText(text);
            sToast.setDuration(duration);
        } else {
            sToast = Toast.makeText(HLibrary.getInstance().getContext(), text, duration);
        }
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }
}
