package com.wslerz.baselibrary.mvvm.base

import android.content.res.Resources
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gyf.immersionbar.ImmersionBar
import com.wslerz.baselibrary.ext.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import me.jessyan.autosize.AutoSizeCompat

/**
 *
 * @author by lzz
 * @date 2020/2/27
 * @description
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseSwipeBackActivity(),
    CoroutineScope by MainScope() {

    protected var isImmersionBarEnabled = true


    protected lateinit var mViewModel: VM


    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
        return super.getResources()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        mViewModel = initVM()
        initStatusBar()
        startExceptionObserve()
        startObserve()
        initData()
        initView()

    }

    protected open fun startExceptionObserve() {
        mViewModel.mExceptionLiveData.observe(this, Observer {
            showToast(it.message)
        })
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun initStatusBar() {
        if (isImmersionBarEnabled) {
            initImmersionBar()
        }
    }

    /**
     * 默认状态栏样式
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)
            .statusBarColor("#ffffff")
            .fitsSystemWindows(true)
            .init()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initVM(): VM
    abstract fun startObserve()
    abstract fun initView()
    abstract fun initData()


    override fun onDestroy() {
        super.onDestroy()
        cancel()

    }
}