package com.wslerz.androidproject.route

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.wslerz.baselibrary.route.IRouterManager
import com.wslerz.baselibrary.util.IApplicationDelegate

/**
 *
 * @author by lzz
 * @date 2020/4/16
 * @description
 */
object RouterManager :IRouterManager,  ICenterProvider, IHomeProvider {

    private val iCenterProvider: ICenterProvider? by lazy {
        ARouter.getInstance().build(RouterPath.ROUTER_PATH_TO_CENTER_SERVICE)
            .navigation() as? ICenterProvider
    }

    private val iHomeProvider: IHomeProvider? by lazy {
        ARouter.getInstance().build(RouterPath.ROUTER_PATH_TO_HOME_SERVICE)
            .navigation() as? IHomeProvider
    }

    override fun getCenterFragment() = iCenterProvider?.getCenterFragment()

    override fun getHomeFragment() = iHomeProvider?.getHomeFragment()

    override fun getIApplicationDelegate(): List<IApplicationDelegate> {
        val list = mutableListOf<IApplicationDelegate>()
        iCenterProvider?.getIApplicationDelegate()?.let { it -> list.addAll(it) }
        iHomeProvider?.getIApplicationDelegate()?.let { it -> list.addAll(it) }
        return list
    }

    override fun init(context: Context?) {

    }
}