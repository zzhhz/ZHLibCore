package com.zzh.lib.core.lang;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Created by ZZH on 3/24/21.
 *
 * @Date: 3/24/21
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
class Charsets {
    /**
     * Returns the given {@code charset} or the default Charset if {@code charset} is null.
     *
     * @param charset a Charset or null.
     * @return the given {@code charset} or the default Charset if {@code charset} is null.
     */
    static Charset toCharset(final Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    /**
     * Returns the given {@code charset} or the default Charset if {@code charset} is null.
     *
     * @param charsetName a Charset or null.
     * @return the given {@code charset} or the default Charset if {@code charset} is null.
     * @throws UnsupportedCharsetException If no support for the named charset is available in this instance of the Java
     *                                     virtual machine
     */
    static Charset toCharset(final String charsetName) {
        return charsetName == null ? Charset.defaultCharset() : Charset.forName(charsetName);
    }

    /**
     * Returns the given {@code charset} or the default Charset if {@code charset} is null.
     *
     * @param charsetName a Charset or null.
     * @return the given {@code charset} or the default Charset if {@code charset} is null.
     */
    static String toCharsetName(final String charsetName) {
        return charsetName == null ? Charset.defaultCharset().name() : charsetName;
    }
}
