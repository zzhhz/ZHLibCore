package com.zzh.lib.core.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zzh.lib.core.HLibrary;

/**
 * Created by ZZH on 2020/3/25.
 *
 * @Date: 2020/3/25
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 键盘工具类
 */
public class HKeyboardUtils {
    /**
     * 关闭activity中打开的键盘
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) HLibrary.getInstance().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 关闭activity中打开的键盘
     *
     * @param view 获取焦点的View
     */
    public static void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) HLibrary.getInstance().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 关闭dialog中打开的键盘
     *
     * @param dialog
     */
    public static void closeKeyboard(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 打开键盘
     *
     * @param editText
     */
    public static void openKeyboard(final EditText editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                editText.setSelection(editText.getText().toString().length());
                InputMethodManager imm = (InputMethodManager) HLibrary.getInstance().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            }
        }, 300);
    }

    /**
     * 拷贝文档到黏贴板
     *
     * @param text
     */
    public static void clip(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) HLibrary.getInstance().getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("content", text));
    }

    /**
     * 切换键盘的显示与隐藏
     */
    public static void toggleKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) HLibrary.getInstance().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 判断软键盘是否打开
     */
    public static boolean isOpenKeyboard() {
        return isOpenKeyboard(null);
    }

    /**
     * 判断软键盘是否打开
     */
    public static boolean isOpenKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) HLibrary.getInstance().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null) {
            return inputMethodManager.isActive();
        } else {
            return inputMethodManager.isActive(view);
        }
    }

    /**
     * 处理点击非 EditText 区域时，自动关闭键盘
     *
     * @param isAutoCloseKeyboard 是否自动关闭键盘
     * @param currentFocusView    当前获取焦点的控件
     * @param motionEvent         触摸事件
     * @param dialogOrActivity    Dialog 或 Activity
     */
    public static void handleAutoCloseKeyboard(boolean isAutoCloseKeyboard, View currentFocusView, MotionEvent motionEvent, Object dialogOrActivity) {
        if (isAutoCloseKeyboard && motionEvent.getAction() == MotionEvent.ACTION_DOWN && currentFocusView != null && (currentFocusView instanceof EditText) && dialogOrActivity != null) {
            int[] leftTop = {0, 0};
            currentFocusView.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + currentFocusView.getHeight();
            int right = left + currentFocusView.getWidth();
            if (!(motionEvent.getX() > left && motionEvent.getX() < right && motionEvent.getY() > top && motionEvent.getY() < bottom)) {
                if (dialogOrActivity instanceof Dialog) {
                    closeKeyboard((Dialog) dialogOrActivity);
                } else if (dialogOrActivity instanceof Activity) {
                    closeKeyboard((Activity) dialogOrActivity);
                }
            }
        }
    }
}
