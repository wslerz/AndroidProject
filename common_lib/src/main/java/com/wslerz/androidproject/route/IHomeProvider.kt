package com.wslerz.androidproject.route

import androidx.fragment.app.Fragment
import com.wslerz.baselibrary.route.IBaseProvider

/**
 *
 * @author by lzz
 * @date 2020/4/16
 * @description
 */
interface IHomeProvider : IBaseProvider {
    fun getHomeFragment(): Fragment?
}