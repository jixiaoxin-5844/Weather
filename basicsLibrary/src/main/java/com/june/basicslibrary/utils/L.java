package com.june.basicslibrary.utils;

import android.util.Log;

import com.june.basicslibrary.BuildConfig;


/**
 * 打印日志
 */
public class L {

    private static final String LOG_FORMAT = "%1$s\n%2$s";

    public static void d(String tag, Object... args) {
        log(Log.DEBUG, null, tag, args);
    }

    public static void i(String tag, Object... args) {
        log(Log.INFO, null, tag, args);
    }

    public static void w(String tag, Object... args) {
        log(Log.WARN, null, tag, args);
    }

    public static void e(Throwable ex) {
        log(Log.ERROR, ex, null);
    }

    public static void e(String tag, Object... args) {
        log(Log.ERROR, null, tag, args);
    }

    public static void e(Throwable ex, String tag, Object... args) {
        log(Log.ERROR, ex, tag, args);
    }

    private static void log(int priority, Throwable ex, String tag, Object... args) {

        if (!BuildConfig.DEBUG)
            return;

        StringBuilder log = new StringBuilder();
        if (ex == null) {
            if (args != null && args.length > 0) {
                for (Object obj : args) {
                    log.append(obj);
                }
            }
        } else {
            String logMessage = ex.getMessage();
            String logBody = Log.getStackTraceString(ex);
            log.append(String.format(LOG_FORMAT, logMessage, logBody));
        }
        Log.println(priority, tag, log.toString());
    }

}
