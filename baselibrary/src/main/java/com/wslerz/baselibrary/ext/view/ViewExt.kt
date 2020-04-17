package com.wslerz.baselibrary.ext.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.wslerz.baselibrary.ext.inputMethodManager

fun View.show(isShow: Boolean = true): View {
    this.visibility = if (isShow) View.VISIBLE else View.GONE
    return this
}

/**
 *
 * @author by lzz
 * @date 2020/3/11
 * @description
 */

fun View.gone(): View {
    this.visibility = View.GONE
    return this
}

fun View.invisibility(): View {
    this.visibility = View.INVISIBLE
    return this
}

/**
 * 获取当前ViewGroup的所有子view
 */
fun ViewGroup.childViews() = MutableList(childCount) {
    getChildAt(it)
}

/**
 * 显示键盘
 */
fun View.showKeyboard() {
    requestFocus()
    context.inputMethodManager?.showSoftInput(this, 0)
}

/**
 * 隐藏键盘
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
        ignored.printStackTrace()
    }
    return false
}

/**
 * 获取当前界面的截图
 */
fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}
