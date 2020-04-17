package com.wslerz.androidproject.home

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.wslerz.androidproject.home.HomeApplicationDelegate
import com.wslerz.androidproject.home.HomeFragment
import com.wslerz.androidproject.route.IHomeProvider
import com.wslerz.androidproject.route.RouterPath

/**
 *
 * @author by lzz
 * @date 2020/4/7
 * @description
 */
@Route(path = RouterPath.ROUTER_PATH_TO_HOME_SERVICE, name = "主页")
class HomeServiceProvider : IHomeProvider {

    override fun getHomeFragment() =
        HomeFragment()

    override fun getIApplicationDelegate() = listOf(HomeApplicationDelegate)

    override fun init(context: Context?) {

    }

}