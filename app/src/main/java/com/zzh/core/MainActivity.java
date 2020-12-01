package com.zzh.core;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zzh.lib.core.model.ResultModel;
import com.zzh.lib.core.utils.HFileUtils;
import com.zzh.lib.core.utils.HMediaUtils;
import com.zzh.lib.core.utils.HResUtils;
import com.zzh.lib.core.utils.LogUtils;
import com.zzh.lib.core.utils.ToastUtils;

/**
 * @Date: 2020/7/17 15:59
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 测试
 */
public class MainActivity extends Activity {

    EditText et_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_text = findViewById(R.id.et_text);
        LogUtils.e(HFileUtils.getPublicDCIMFile().getAbsolutePath());
        LogUtils.e("nav bar height: " + HResUtils.getNavBarHeight());
        LogUtils.e("status bar height: " + HResUtils.getStatusBarHeight());
    }

    public void onClickView(View view) {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            ResultModel model = HMediaUtils.savePicture("ccccc.jpg", bitmap);
            String path = HMediaUtils.queryAbsolutePath(model.getUri());
            LogUtils.e(path);
            ImageView iv = findViewById(R.id.iv);
            iv.setImageBitmap(BitmapFactory.decodeFile(path));
            ToastUtils.show("保存成功");
            return;
        }
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 0);
    }
}
