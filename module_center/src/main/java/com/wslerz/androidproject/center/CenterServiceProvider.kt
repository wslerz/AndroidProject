package com.wslerz.androidproject.center

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.wslerz.androidproject.center.CenterApplicationDelegate
import com.wslerz.androidproject.center.CenterFragment
import com.wslerz.androidproject.route.ICenterProvider
import com.wslerz.androidproject.route.RouterPath

/**
 *
 * @author by lzz
 * @date 2020/4/7
 * @description
 */
@Route(path = RouterPath.ROUTER_PATH_TO_CENTER_SERVICE, name = "个人中心")
class CenterServiceProvider : ICenterProvider {

    override fun getCenterFragment() =
        CenterFragment()

    override fun getIApplicationDelegate() = listOf(CenterApplicationDelegate)

    override fun init(context: Context?) {

    }

}