package com.wslerz.baselibrary.base

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * @author by lzz
 * @date 2020/2/21
 * @description
 */
abstract class SimpleBaseQuickAdapter<T>(@LayoutRes layoutResId: Int, list: MutableList<T>? = null) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutResId, list) {
//    override fun convert(helper: BaseViewHolder, item: T?) {
//        item ?: return
//        convertData(helper, item)
//    }

    abstract fun convertData(helper: BaseViewHolder, item: T)
    override fun convert(helper: BaseViewHolder, item: T) {
        item ?: return
        convertData(helper, item)
    }
}


