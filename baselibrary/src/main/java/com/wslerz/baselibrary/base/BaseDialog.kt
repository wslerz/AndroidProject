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
abstract class BaseDialog(mContext: Context) :
    Dialog(mContext) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initWindow()
        initView()
    }

    protected abstract fun initView()

    protected fun setGravity(gravity: Int) {
        val window = this.window
        window?.setGravity(gravity)
    }

    protected fun setWidth(width: Int) {
        val window = this.window
        window?.run {
            val params = attributes
            params.width = width //宽度设置为屏幕
            attributes = params
        }
    }

    private fun initWindow() {
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        val window = this.window
        window?.run {
            val params = attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT //宽度设置为屏幕
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            attributes = params
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawable(context.resources.getDrawable(R.color.base_transparent, null))
        }
    }

    protected abstract fun getLayoutId(): Int

}