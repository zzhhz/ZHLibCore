package com.zzh.lib.core.lang.function;

/**
 * Created by ZZH on 3/24/21.
 *
 * @Date: 3/24/21
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
@FunctionalInterface
public interface ToBooleanBiFunction<T, U> {
    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument.
     * @param u the second function argument.
     * @return the function result.
     */
    boolean applyAsBoolean(T t, U u);
}
