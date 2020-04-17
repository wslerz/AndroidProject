package com.wslerz.baselibrary.util

import android.util.Log
import com.wslerz.baselibrary.BuildConfig

/**
 *
 * @author by lzz
 * @date 2020/2/19
 * @description
 */
object LogUtils {
    private val isDebug = BuildConfig.DEBUG

    fun i(log: String) {
        if (isDebug) {
            Log.i("lzz", log)
        }
    }

}