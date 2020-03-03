package com.zzh.lib.core.listener;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.zzh.lib.core.HLibrary;

/**
 * Created by ZZH on 2020-03-02.
 *
 * @Date: 2020-03-02
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 监听软键盘是否弹出
 */
public class HKeyboardChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "ListenerHandler";
    private View mContentView;
    private int mOriginHeight;
    private int mPreHeight;
    private KeyBoardListener mKeyBoardListen;

    public interface KeyBoardListener {
        /**
         * call back
         *
         * @param isShow         true 弹出, false 隐藏
         * @param keyboardHeight 软键盘的高度
         */
        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

    public static void addKeyBoardListener(Activity atx, KeyBoardListener keyBoardListener) {
        if (atx == null) {
            atx = HLibrary.getLastActivity();
        }
        new HKeyboardChangeListener(atx).setKeyBoardListener(keyBoardListener);
    }

    public void setKeyBoardListener(KeyBoardListener keyBoardListen) {
        this.mKeyBoardListen = keyBoardListen;
    }

    public HKeyboardChangeListener(Activity contextObj) {
        if (contextObj == null) {
            return;
        }
        mContentView = findContentView(contextObj);
        if (mContentView != null) {
            addContentTreeObserver();
        }
    }

    private View findContentView(Activity contextObj) {
        return contextObj.findViewById(android.R.id.content);
    }

    private void addContentTreeObserver() {
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        int currHeight = mContentView.getHeight();
        if (currHeight == 0) {
            return;
        }
        boolean hasChange = false;
        if (mPreHeight == 0) {
            mPreHeight = currHeight;
            mOriginHeight = currHeight;
        } else {
            if (mPreHeight != currHeight) {
                hasChange = true;
                mPreHeight = currHeight;
            } else {
                hasChange = false;
            }
        }
        if (hasChange) {
            boolean isShow;
            int keyboardHeight = 0;
            if (mOriginHeight == currHeight) {
                //hidden
                isShow = false;
            } else {
                //show
                keyboardHeight = mOriginHeight - currHeight;
                isShow = true;
            }

            if (mKeyBoardListen != null) {
                mKeyBoardListen.onKeyboardChange(isShow, keyboardHeight);
            }
        }
    }
}
