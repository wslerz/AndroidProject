package com.wslerz.baselibrary.util

import android.content.Context
import androidx.annotation.Keep
import com.wslerz.baselibrary.base.BaseApp
import org.koin.core.module.Module

/**
 *
 * @author by lzz
 * @date 2020/4/4
 * @description ApplicationDelegate
 */
@Keep
interface IApplicationDelegate {
    fun onCreate(baseApp: BaseApp)

    fun onTerminate()

    fun onLowMemory()

    fun onTrimMemory(level: Int)

    fun attachBaseContext(base: Context)

    /**
     * koin
     */
    fun getAppModules(): List<Module>

}