package com.zzh.lib.core.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;

import com.zzh.lib.core.HLibrary;
import com.zzh.lib.core.model.ResultModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ZZH on 2020/11/30.
 *
 * @Date: 2020/11/30
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 媒体操作工具。保存图片到相册。
 */
public class HMediaUtils {


    /**
     * 保存文件
     *
     * @param fileName 图片名称
     * @param bitmap   图片资源
     */
    public static ResultModel savePicture(String fileName, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Long mImageTime = System.currentTimeMillis();
            final ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000);
            values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000);
            values.put(MediaStore.MediaColumns.DATE_EXPIRES, (mImageTime + DateUtils.DAY_IN_MILLIS) / 1000);
            values.put(MediaStore.MediaColumns.IS_PENDING, 1);
            ContentResolver resolver = HLibrary.getInstance().getContext().getContentResolver();
            final Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            try {
                try (OutputStream out = resolver.openOutputStream(uri)) {
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                        throw new IOException("Failed to compress");
                    }
                    out.flush();
                }
                values.clear();
                values.put(MediaStore.MediaColumns.IS_PENDING, 0);
                values.putNull(MediaStore.MediaColumns.DATE_EXPIRES);
                LogUtils.d("-------图片路径：" + uri.toString());
                resolver.update(uri, values, null, null);
                return new ResultModel(true, uri);
            } catch (IOException e) {
                resolver.delete(uri, null);
                Log.d("Exception", e.toString());
                return new ResultModel(false);
            }
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            String pathName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + fileName;
            boolean file = HFileUtils.saveFile(baos.toByteArray(), pathName);
            return new ResultModel(file, pathName);
        }
    }

    /**
     * 保存文件
     *
     * @param fileName 图片名称
     * @param bitmap   图片字节数组
     */
    public static ResultModel savePicture(String fileName, byte[] bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Long mImageTime = System.currentTimeMillis();
            final ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000);
            values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000);
            values.put(MediaStore.MediaColumns.DATE_EXPIRES, (mImageTime + DateUtils.DAY_IN_MILLIS) / 1000);
            values.put(MediaStore.MediaColumns.IS_PENDING, 1);

            ContentResolver resolver = HLibrary.getInstance().getContext().getContentResolver();
            final Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            try {
                try (OutputStream out = resolver.openOutputStream(uri)) {
                    out.write(bitmap);
                    out.flush();
                }
                values.clear();
                values.put(MediaStore.MediaColumns.IS_PENDING, 0);
                values.putNull(MediaStore.MediaColumns.DATE_EXPIRES);
                resolver.update(uri, values, null, null);
                return new ResultModel(true, uri);
            } catch (IOException e) {
                resolver.delete(uri, null);
                Log.d("Exception", e.toString());
                return new ResultModel(false);
            }
        } else {
            String pathName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + fileName;
            boolean success = HFileUtils.saveFile(bitmap, pathName);

            return new ResultModel(success, pathName);
        }
    }

    /**
     * 获取文件的绝对路径
     *
     * @param uri uri
     * @return path
     */
    public static String queryAbsolutePath(Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(HLibrary.getInstance().getContext(), uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取文件的绝对路径
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    private static String getDataColumn(Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = HLibrary.getInstance().getContext().getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
