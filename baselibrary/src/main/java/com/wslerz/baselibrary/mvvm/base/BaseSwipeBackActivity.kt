package com.wslerz.baselibrary.mvvm.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.cxz.swipelibrary.SwipeBackActivityBase
import com.cxz.swipelibrary.SwipeBackActivityHelper
import com.cxz.swipelibrary.SwipeBackLayout
import com.cxz.swipelibrary.Utils

/**
 *
 * @author by lzz
 * @date 2020/3/25
 * @description
 */
open class BaseSwipeBackActivity : AppCompatActivity(), SwipeBackActivityBase {
    private lateinit var mHelper: SwipeBackActivityHelper

    /**
     * 是否可以侧滑退出
     */
    open fun enableSwipeBack(): Boolean = true

    private fun initSwipeBack() {
        setSwipeBackEnable(enableSwipeBack())
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
        initSwipeBack()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mHelper.swipeBackLayout
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }
}