package com.zzh.lib.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.zzh.lib.core.HLibrary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator.
 *
 * @date: 2018/11/6
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ZzhLibs 文件操作
 * @since 1.0
 */
public class HFileUtils {

    public static final int MEM_SAVE_SIZE_UNIT = 1024;

    /**
     * 文件操作
     *
     * @param data         字节
     * @param filePathName 写入的文件路径
     * @return 写入成功 true
     */
    public static boolean saveFile(byte[] data, String filePathName) {
        if (data == null || TextUtils.isEmpty(filePathName)) {
            return false;
        }
        return saveFile(data, new File(filePathName));
    }

    /**
     * 文件操作
     *
     * @param data         字节
     * @param filePathName 写入的文件
     * @return 写入成功 true
     */
    public static boolean saveFile(byte[] data, File filePathName) {
        BufferedOutputStream bos = null;
        if (isMountedSDCard()) {
            try {
                bos = new BufferedOutputStream(new FileOutputStream(filePathName));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 私有缓存目录。如果需要缓存多重类型的文件，建议在此目录下再次进行新建目录划分
     * <p>
     * /storage/emulated/0/Android/data/xxx.xxx.xx(包名)/cache 或者 /data/data/xxx.xxx.xx/
     *
     * @return 缓存路径, 文件夹
     */
    public static File getCacheFile() {
        File cacheDir = null;
        if (isMountedSDCard()) {
            cacheDir = getExternalCacheDir();
        } else {
            cacheDir = getCacheDir();
        }
        return cacheDir;
    }

    /**
     * 私有缓存目录。如果需要缓存多重类型的文件，建议在此目录下再次进行新建目录划分
     * /storage/emulated/0/Android/data/xxx.xxx.xx(包名)/cache
     *
     * @return 缓存路径 文件夹
     */
    public static File getExternalCacheDir() {
        return HLibrary.getInstance().getContext().getExternalCacheDir();
    }

    /**
     * 私有缓存目录。如果需要缓存多重类型的文件，建议在此目录下再次进行新建目录划分
     * <p>
     * /data/data/xxx.xxx.xx/
     *
     * @return 缓存路径 文件夹
     */
    public static File getCacheDir() {
        return HLibrary.getInstance().getContext().getCacheDir();
    }

    /**
     * SD卡 私有目录路径
     * <p>
     * /storage/emulated/0/Android/data/xxx.xxx.xx(包名)/
     *
     * @return 私有目录路径
     */
    public static String getExternalPath(Context ctx) {
        return ctx.getExternalCacheDir().getParent();
    }

    /**
     * SD卡 私有目录路径
     * <p>
     * /storage/emulated/0/Android/data/xxx.xxx.xx(包名)/
     *
     * @return 私有目录路径
     */
    public static File getExternalFile() {
        return HLibrary.getInstance().getContext().getExternalCacheDir().getParentFile();
    }

    /**
     * SD卡 私有目录路径
     * <p>
     * /storage/emulated/0/Android/data/xxx.xxx.xx(包名)/
     *
     * @return 私有目录路径
     */
    public static String getExternalPath() {
        return getExternalPath(HLibrary.getInstance().getContext());
    }

    /**
     * 私有目录路径
     * <p>
     * /data/data/xxx.xxx.xx/
     *
     * @return 私有目录路径
     */
    public static String getPrivatePath(Context ctx) {
        return ctx.getCacheDir().getParent();
    }

    /**
     * 私有目录路径
     * <p>
     * /data/data/xxx.xxx.xx/
     *
     * @return 私有目录路径
     */
    public static String getPrivatePath() {
        return getPrivatePath(HLibrary.getInstance().getContext());
    }

    /**
     * 私有目录路径
     * <p>
     * /data/data/xxx.xxx.xx/
     *
     * @return 私有目录路径
     */
    public static File getPrivateFile() {
        return HLibrary.getInstance().getContext().getCacheDir().getParentFile();
    }

    /**
     * 数据库私有路径
     * <p>
     * /data/data/xxx.xxx.xx/databases
     *
     * @return 私有目录路径
     */
    public static File getDatabaseFile() {
        File file = new File(getPrivateFile(), "databases");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * SD卡数据库目录路径
     * <p>
     * /storage/emulated/0/Android/data/xxx.xxx.xx/databases
     *
     * @return 私有目录路径
     */
    public static File getExternalDatabaseFile() {
        File file = new File(getPrivateFile(), "databases");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取可用的SD卡路径（若SD卡不没有挂载则返回""）
     *
     * @return
     */
    public static String getSDCardRootPath() {
        if (isMountedSDCard()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            if (!sdcardDir.canWrite()) {
                LogUtils.w("-----SDCARD can not write !----");
            }
            return sdcardDir.getPath();
        }
        return "";
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建文件是否成功。
     */
    public static boolean createFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            File parentFile = file.getParentFile();
            boolean mkdirs = parentFile.mkdirs();
            if (mkdirs) {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * 检查是否已挂载SD卡镜像（是否存在SD卡）
     *
     * @return
     */
    public static boolean isMountedSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            LogUtils.w("----没有SD 卡----");
            return false;
        }
    }

    /**
     * 获取应用缓存目录的绝对路径，优先使用外部SD卡的缓存路径
     *
     * @param context
     * @return 缓存目录的绝对路径
     */
    public static String getDiskCacheDir(Context context) {
        File cacheDir = context.getCacheDir();
        if (isMountedSDCard()) {
            File nCacheDir = context.getExternalCacheDir();
            // 部分手机有卡不可写
            if (nCacheDir != null) {
                if (nCacheDir.canWrite()) {
                    cacheDir = nCacheDir;
                }
            }
        }
        return cacheDir.getAbsolutePath();
    }

    /**
     * 获取应用缓存目录的绝对路径，优先使用外部SD卡的缓存路径
     *
     * @return 缓存目录的绝对路径
     */
    public static String getDiskCacheDir() {
        File cacheDir = HLibrary.getInstance().getContext().getCacheDir();
        if (isMountedSDCard()) {
            File nCacheDir = HLibrary.getInstance().getContext().getExternalCacheDir();
            // 部分手机有卡不可写
            if (nCacheDir != null) {
                if (nCacheDir.canWrite()) {
                    cacheDir = nCacheDir;
                }
            }
        }
        return cacheDir.getAbsolutePath();
    }

    /**
     * 获取应用文件的缓存目录
     * <p>
     * /data/data/xxx.xxx.xx(包名)/files/
     *
     * @param context
     * @return 缓存目录的绝对路径
     */
    public static File getFileDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * 获取应用文件的缓存目录
     * <p>
     * /data/data/xxx.xxx.xx(包名)/files/
     *
     * @return 文件缓存目录
     */
    public static File getFileDir() {
        return getFileDir(HLibrary.getInstance().getContext());
    }

    /**
     * 获取SD卡剩余容量（单位Byte）
     *
     * @return
     */
    public static long gainSDFreeSize() {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 空闲的数据块的数量
            long freeBlocks = sf.getAvailableBlocks();

            // 返回SD卡空闲大小
            return freeBlocks * blockSize; // 单位Byte
        } else {
            return 0;
        }
    }

    /**
     * 获取SD卡总容量（单位Byte）
     *
     * @return SD卡的总容量
     */
    public static long getSDAllSize() {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 获取所有数据块数
            long allBlocks = sf.getBlockCount();
            // 返回SD卡大小（Byte）
            return allBlocks * blockSize;
        } else {
            return 0;
        }
    }

    /**
     * 格式化文件大小
     *
     * @param size 文件大小
     * @return 格式化后的大小
     */
    public static String getFormatSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < MEM_SAVE_SIZE_UNIT) {
            return size + "bytes";
        } else if (size < MEM_SAVE_SIZE_UNIT * MEM_SAVE_SIZE_UNIT) {
            float kbSize = size / 1024f;
            return formater.format(kbSize) + "KB";
        } else if (size < MEM_SAVE_SIZE_UNIT * MEM_SAVE_SIZE_UNIT * MEM_SAVE_SIZE_UNIT) {
            float mbSize = size / 1024f / 1024f;
            return formater.format(mbSize) + "MB";
        } else if (size < MEM_SAVE_SIZE_UNIT * MEM_SAVE_SIZE_UNIT * MEM_SAVE_SIZE_UNIT * MEM_SAVE_SIZE_UNIT) {
            float gbSize = size / 1024f / 1024f / 1024f;
            return formater.format(gbSize) + "GB";
        } else {
            return "size: error";
        }
    }

    /**
     * 针对视频文件，格式化播放时长
     *
     * @param time 视频文件总播放长度
     * @return 格式化后的时间
     */
    public static String getFormatDateTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(time);
    }

    /**
     * 读取文本文件中的一行
     *
     * @param path 文件路径
     * @return 返回读取到的字符串
     */
    public static String readLine(String path) throws IOException {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        return reader.readLine();
    }

    /**
     * 读取文本文件
     *
     * @param path 文本文件路径
     * @return
     */
    public static String readText(String path) {
        byte[] read = read(path);
        return new String(read);
    }

    /**
     * 读取文件
     *
     * @param path 文件路径
     * @return 返回字节数组
     */
    public static byte[] read(String path) {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            bis = new BufferedInputStream(new FileInputStream(new File(path)));
            byte[] buffer = new byte[8 * 1024];
            int c = 0;
            while ((c = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将bitmap转换成byte数组
     *
     * @param bmp         bitmap实例
     * @param needRecycle 是否需要回收
     * @return 字节数组
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取文件的公共路径 当版本小于Q时使用此方法
     *
     * @return
     */
    public static File getPublicFile(String type) {
        return Environment.getExternalStoragePublicDirectory(type);
    }

    /**
     * 图片文件路径，当版本小于Q时使用此方法
     *
     * @return
     */
    public static File getPublicPictureFile() {
        return getPublicFile(Environment.DIRECTORY_PICTURES);
    }

    public static File getPublicDCIMFile(){
        return getPublicFile(Environment.DIRECTORY_DCIM);
    }
    public static File getPublicDownloadFile(){
        return getPublicFile(Environment.DIRECTORY_DOWNLOADS);
    }
    public static File getPublicMusicFile(){
        return getPublicFile(Environment.DIRECTORY_MUSIC);
    }
}
