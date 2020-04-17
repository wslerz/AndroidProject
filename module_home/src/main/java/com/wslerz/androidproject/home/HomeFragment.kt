package com.wslerz.androidproject.home

import com.wslerz.androidproject.R
import com.wslerz.baselibrary.mvvm.base.BaseVMTabFragment
import org.koin.android.ext.android.get

/**
 *
 * @author by lzz
 * @date 2020/4/16
 * @description
 */
class HomeFragment : BaseVMTabFragment<HomeViewModel>() {
    override fun initVM(): HomeViewModel = get()
    override fun getLayoutResId() = R.layout.home_fragment_home

    override fun startObserve() {
    }

    override fun business() {
    }
}