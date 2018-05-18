package com.example.common.common.utils;

import android.util.Log;

/**
 *功能：log工具类
 * @author wangxiaoer
 *
 * 2015年10月16日下午2:49:29
 */
public class Logger {
    private static boolean isDebug = true;

    public static void exception(Exception e) {
        if(isDebug) {
            e.printStackTrace();
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable t) {
        if (isDebug)
            Log.v(tag, msg, t);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (isDebug)
            Log.d(tag, msg, t);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable t) {
        if (isDebug)
            Log.i(tag, msg, t);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable t) {
        if (isDebug)
            Log.w(tag, msg, t);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        if (isDebug)
            Log.e(tag, msg, t);
    }
}
