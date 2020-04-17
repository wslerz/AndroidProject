package com.wslerz.baselibrary.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 *
 *@author by lzz
 *@date 2020/2/17
 *@description
 */

object GlideUtils {
    @JvmStatic
    fun load(
        context: Context?,
        url: String?,
        iv: ImageView?
    ) {
        Glide.with(context!!)
            .load(url)
            .thumbnail(0.1f)
            .centerCrop()
            .into(iv!!)
    }

    @JvmStatic
    fun loadRound(
        context: Context?,
        url: String?,
        roundingRadius: Int,
        iv: ImageView?
    ) {
        val roundedCorners = RoundedCorners(roundingRadius);
        val options = RequestOptions.bitmapTransform(roundedCorners).override(0, 0)
        Glide.with(context!!)
            .load(url)
            .apply(options)
            .thumbnail(0.1f)
            .centerCrop()
            .into(iv!!)
    }


    @JvmStatic
    fun load(
        context: Context?,
        url: String?,
        def: Int,
        iv: ImageView?
    ) {
        Glide.with(context!!)
            .load(url)
            .thumbnail(0.1f)
            .placeholder(def)
            .error(def)
            .centerCrop()
            .into(iv!!)
    }

    @JvmStatic
    fun loadCircle(
        context: Context?,
        url: String?,
        iv: ImageView?
    ) {
        Glide.with(context!!)
            .load(url)
            .circleCrop()
            .thumbnail(0.1f)
            .centerCrop()
            .into(iv!!)
    }

    /**
     * @param def user_head_default 用户   hamal_head_default
     */
    fun loadCircle(
        context: Context?,
        url: String?,
        def: Int,
        iv: ImageView?
    ) {
        Glide.with(context!!)
            .load(url)
            .placeholder(def)
            .error(def)
            .circleCrop()
            .thumbnail(0.1f)
            .centerCrop()
            .into(iv!!)
    }
}
