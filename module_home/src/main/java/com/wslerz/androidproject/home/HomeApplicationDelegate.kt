package com.wslerz.androidproject.home

import android.content.Context
import com.wslerz.androidproject.home.appModules
import com.wslerz.baselibrary.base.BaseApp
import com.wslerz.baselibrary.util.IApplicationDelegate

/**
 *
 * @author by lzz
 * @date 2020/4/7
 * @description
 */
object HomeApplicationDelegate : IApplicationDelegate {
    override fun onCreate(baseApp: BaseApp) {
    }

    override fun onTerminate() {
    }

    override fun onLowMemory() {
    }

    override fun onTrimMemory(level: Int) {

    }

    override fun attachBaseContext(base: Context) {
    }

    override fun getAppModules() = appModules
}