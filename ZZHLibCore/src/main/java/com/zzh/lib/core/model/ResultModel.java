package com.zzh.lib.core.model;

import android.net.Uri;

/**
 * Created by ZZH on 2020/12/1.
 *
 * @Date: 2020/12/1
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 操作结果处理
 */
public class ResultModel {
    private boolean success;
    private Uri uri;
    private String path;

    public ResultModel(boolean success, Uri uri, String path) {
        this.success = success;
        this.uri = uri;
        this.path = path;
    }

    public ResultModel(boolean success, Uri uri) {
        this.success = success;
        this.uri = uri;
    }

    public ResultModel(boolean success, String path) {
        this.success = success;
        this.path = path;
    }

    public ResultModel(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
