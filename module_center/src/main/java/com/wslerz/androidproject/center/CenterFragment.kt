package com.wslerz.androidproject.center

import com.wslerz.baselibrary.mvvm.base.BaseVMTabFragment
import org.koin.android.ext.android.get

/**
 *
 * @author by lzz
 * @date 2020/4/16
 * @description
 */
class CenterFragment : BaseVMTabFragment<CenterViewModel>() {
    override fun initVM(): CenterViewModel = get()
    override fun getLayoutResId() = R.layout.center_fragment_center

    override fun startObserve() {
    }

    override fun business() {
    }
}