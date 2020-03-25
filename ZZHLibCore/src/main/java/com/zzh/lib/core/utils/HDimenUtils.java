package com.zzh.lib.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZZH on 2020-02-08.
 *
 * @Date: 2020-02-08
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class HDimenUtils {

    /**
     * 判断手机号是否合法
     *
     * @param mobiles 手机号
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((19[0-9])|(14[5-7])|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
