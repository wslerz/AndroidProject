package com.wslerz.baselibrary.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * @author by lzz
 * @date 2020/2/17
 * @description
 */
object ScreenUtil {
    /**
     * 获得屏幕高度
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获得屏幕宽度
     */
    fun getScreenHeight(context: Context): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 获得状态栏的高度
     */
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    fun snapShotWithStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width =
            getScreenWidth(activity)
        val height =
            getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp
    }

    fun dp2px(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }


    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width =
            getScreenWidth(activity)
        val height =
            getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(
            bmp, 0, statusBarHeight, width, height
                    - statusBarHeight
        )
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 按16:9重新计算高度
     */
    fun resizeViewGroup(context: Context, viewGroup: ViewGroup) {
        val screenWidth =
            getScreenWidth(context)
        val playerHeight = screenWidth * 9 / 16
        val layoutParams = viewGroup.layoutParams
        layoutParams.height = playerHeight
        viewGroup.layoutParams = layoutParams
    }

    /**
     * 浸入式状态栏
     */
    fun initStatusWindow(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (activity.window != null) {
                val decorView = activity.window.decorView
                val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                decorView.systemUiVisibility = option
                activity.window.statusBarColor = Color.TRANSPARENT
            }
        }
        val actionBar = activity.supportActionBar
        actionBar?.hide()
    }
}