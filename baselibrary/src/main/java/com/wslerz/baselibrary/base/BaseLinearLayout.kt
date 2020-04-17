package com.wslerz.baselibrary.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes

/**
 *
@author by lzz
@date 2020/1/13
@description
 */
abstract class BaseLinearLayout(
    context: Context,
    attributeSet: AttributeSet?,
    @LayoutRes val layoutResId: Int
) : LinearLayout(context, attributeSet, -1) {
    protected val mContext: Context = context
    protected val view: View

    init {
        view = LayoutInflater.from(mContext).inflate(layoutResId, this, true)
        initView(attributeSet)
    }
    abstract fun initView(attributeSet: AttributeSet?)
}