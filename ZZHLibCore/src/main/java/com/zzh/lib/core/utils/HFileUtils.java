package com.zzh.lib.core.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
     *
     * @return 缓存路径
     */
    public static String getPrivateCachePath(Context ctx) {
        File cacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cacheDir = ctx.getExternalCacheDir();
        } else {
            cacheDir = ctx.getCacheDir();
        }
        return cacheDir.getAbsolutePath();
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
     * 获取可用的SD卡文件实例
     *
     * @return SD卡文件实例
     */
    public static File getSDCardRootFile() {
        if (isMountedSDCard()) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    /**
     * 返回指定的文件路径
     *
     * @param type 文件类型
     * @return
     */
    public static String getSDCardDirectory(String type) {
        if (isMountedSDCard()) {
            return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
        }
        return null;
    }

    /**
     * 创建文件夹
     *
     * @param filePath 文件夹路径
     * @return
     */
    public static boolean createSDCardDirectory(String filePath) {
        if (isMountedSDCard()) {
            String sdCardRootPath = getSDCardRootPath();
            File file = new File(sdCardRootPath, filePath);
            if (!file.exists()) {
                return file.mkdirs();
            }
        }
        return false;
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建文件是否成功。
     */
    public static boolean createSDCardFile(String path) {
        if (isMountedSDCard()) {
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
                    }
                } else {
                    return false;
                }
            }
        }

        return false;
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
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbSize = size / 1024f;
            return formater.format(kbSize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbSize = size / 1024f / 1024f;
            return formater.format(mbSize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
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
}
