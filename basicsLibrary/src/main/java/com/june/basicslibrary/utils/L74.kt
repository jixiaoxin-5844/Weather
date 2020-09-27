package com.june.basicslibrary.utils

import android.util.Log
import com.june.basicslibrary.BuildConfig

/**
 *<pre>
 * author : Hyt
 * time : 2020/07/04
 * version : 1.0
 * 打印日志类
 *</pre>
 */
class L74 {

    companion object {

        private val LOG_FORMAT = "%1\$s\n%2\$s"

        fun d(tag: String?, vararg args: Any?) {
            log(Log.DEBUG, null, tag, args)
        }

        fun i(tag: String?, vararg args: Any?) {
            log(Log.INFO, null, tag, args)
        }

        fun w(tag: String?, vararg args: Any?) {
            log(Log.WARN, null, tag, args)
        }

        fun e(ex: Throwable?) {
            log(Log.ERROR, ex, null)
        }

        fun e(tag: String?, vararg args: Any?) {
            log(Log.ERROR, null, tag, args)
        }

        fun e(ex: Throwable?, tag: String?, vararg args: Any?) {
            log(Log.ERROR, ex, tag, args)
        }

        private fun log(
            priority: Int,
            ex: Throwable?,
            tag: String?,
            vararg args: Any
        ) {
            if (!BuildConfig.DEBUG) return
            val log = StringBuilder()
            if (ex == null) {
                if (args.isNotEmpty()) {
                    for (obj in args) {
                        log.append(obj)
                    }
                }
            } else {
                val logMessage = ex.message
                val logBody = Log.getStackTraceString(ex)
                log.append(String.format(LOG_FORMAT, logMessage, logBody))
            }
            Log.println(priority, tag, log.toString())
        }
    }
}