package com.wslerz.baselibrary.util

import android.content.Context
import android.widget.Toast

/**
 *
 *@author by lzz
 *@date 2020/2/17
 *@description  吐司工具类
 */
private var mToast: Toast? = null

fun showToast(context: Context, message: String?) {
    message?.let {
        if (mToast != null) {
            mToast?.cancel()
        }
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        mToast?.show()
    }
}

