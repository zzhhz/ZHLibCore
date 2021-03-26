package com.zzh.lib.core.lang;

/**
 * Created by ZZH on 3/24/21.
 *
 * @Date: 3/24/21
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class ObjectUtils {

    public static boolean allNotNull(final Object... values) {
        if (values == null) {
            return false;
        }

        for (final Object val : values) {
            if (val == null) {
                return false;
            }
        }

        return true;
    }
}
