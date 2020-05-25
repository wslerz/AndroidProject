package com.wslerz.baselibrary.util

import android.util.Log

/**
 *
 * @author by lzz
 * @date 2020/2/19
 * @description
 */
object LogUtils {
    private var isDebug = false
    private var TAG = "LogUtils"

    fun setTag(tag: String): LogUtils {
        TAG = tag
        return this
    }

    fun isDebug(isDebug: Boolean): LogUtils {
        this.isDebug = isDebug
        return this
    }

    fun time(log: String) {
        if (isDebug) {
            Log.i(TAG, "$log : ${System.currentTimeMillis()}")
        }
    }

    fun i(log: String) {
        if (isDebug) {
            Log.i(TAG, log)
        }
    }

}