package com.wslerz.baselibrary.mvvm.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.wslerz.baselibrary.ext.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 *
 * @author by lzz
 * @date 2020/2/27
 * @description
 */
abstract class BaseVMTabFragment<VM : BaseViewModel> : SimpleImmersionFragment(),
    CoroutineScope by MainScope() {

    //是否是第一次加载数据
    private var isFirstLoad = true
    protected lateinit var mContext: Context

    protected lateinit var mViewModel: VM

    override fun initImmersionBar() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)

    }

    override fun onResume() {
        super.onResume();
        if (isFirstLoad) {
            initView();
            mViewModel = initVM()
            startExceptionObserve()
            startObserve()
            business()
            isFirstLoad = false;
        }
    }

    protected open fun startExceptionObserve() {
        mViewModel.mExceptionLiveData.observe(this, Observer {
            mContext.showToast(it.message ?: "")
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        isFirstLoad = true
    }

    abstract fun initVM(): VM
    abstract fun getLayoutResId(): Int

    abstract fun startObserve()

    protected open fun initView() {}

    protected abstract fun business()


    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}