package com.zzh.lib.core.lang;

import java.util.Locale;

/**
 * Created by ZZH on 3/24/21.
 *
 * @Date: 3/24/21
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class LocaleUtils {
    /**
     * Returns the given locale if non-{@code null}, otherwise {@link Locale#getDefault()}.
     *
     * @param locale a locale or {@code null}.
     * @return the given locale if non-{@code null}, otherwise {@link Locale#getDefault()}.
     * @since 3.12.0
     */
    public static Locale toLocale(final Locale locale) {
        return locale != null ? locale : Locale.getDefault();
    }
}
