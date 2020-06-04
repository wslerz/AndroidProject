package com.wslerz.baselibrary.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.wslerz.baselibrary.R

/**
 * @author by lzz
 * @date 2019/12/8
 * @description Dialog基类
 */
abstract class BaseDialog(
    mContext: Context,
    private val layoutId: Int,
    private val width: Int = WindowManager.LayoutParams.MATCH_PARENT,
    private val height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    private val gravity: Int = Gravity.BOTTOM
) : Dialog(mContext) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        initWindow()
        initView()
    }

    abstract fun initView()

    protected fun setGravity(gravity: Int) {
        val window = this.window
        window?.setGravity(gravity)
    }

    protected fun setLayoutParams(
        width: Int = WindowManager.LayoutParams.MATCH_PARENT,
        height: Int = WindowManager.LayoutParams.WRAP_CONTENT
    ) {
        val window = this.window
        window?.run {
            val params = attributes
            params.width = width //宽度设置为屏幕
            params.height = height
            attributes = params
        }
    }

    private fun initWindow() {
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        val window = this.window
        window?.run {
            val params = attributes
            params.width = width
            params.height = height
            attributes = params
            setGravity(gravity)
            setBackgroundDrawable(context.resources.getDrawable(R.color.base_transparent, null))
        }
    }


}