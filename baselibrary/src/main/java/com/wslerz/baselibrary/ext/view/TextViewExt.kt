package com.wslerz.baselibrary.ext.view

import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat.getColor

/**
 *
 * @author by lzz
 * @date 2020/2/27
 * @description
 */
fun Button.textColor(@ColorRes id: Int) {
    setTextColor(getColor(this.context, id))
}

fun TextView.textColor(@ColorRes id: Int) {
    setTextColor(getColor(this.context, id))
}

fun TextView.trimText() = text.toString().trim()