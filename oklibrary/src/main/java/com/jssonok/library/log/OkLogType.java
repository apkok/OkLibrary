package com.jssonok.library.log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 日志打印形式类
 */
public class OkLogType {

    /**
     * 1. 注解的保留时期为源码级别
     * 2. 注解所接受的一些类型为int类型的V, D, A, E, I, W
     */
    @IntDef({V, D, I, A, W, E})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE{}

    public final static int V = Log.VERBOSE;
    public final static int D = Log.DEBUG;
    public final static int I = Log.INFO;
    public final static int A = Log.ASSERT;
    public final static int W = Log.WARN;
    public final static int E = Log.ERROR;
}
