package com.zzh.core;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;

import com.zzh.lib.core.HLibrary;
import com.zzh.lib.core.utils.HDimenUtils;
import com.zzh.lib.core.utils.HResUtils;
import com.zzh.lib.core.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HLibrary.getInstance().init(this.getApplication());
        HLibrary.setDebug(true);
        int height = getStatusBarHeight();
        LogUtils.e(String.valueOf(height));
        LogUtils.e(String.valueOf(HDimenUtils.px2dp(84)));
        LogUtils.e(String.valueOf(HDimenUtils.dp2px(33)));
        LogUtils.e(String.valueOf(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 85, getResources().getDisplayMetrics())));
        LogUtils.e(String.valueOf(HResUtils.getStatusBarHeight()));
    }

    private int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
