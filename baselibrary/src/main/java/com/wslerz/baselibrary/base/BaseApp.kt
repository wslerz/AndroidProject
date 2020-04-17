package com.wslerz.baselibrary.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.wslerz.baselibrary.BuildConfig
import com.wslerz.baselibrary.route.IRouterManager
import com.wslerz.baselibrary.util.CustomActivityLifecycleCallbacks
import com.wslerz.baselibrary.util.IApplicationDelegate
import com.wslerz.baselibrary.util.SPUtils
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import kotlin.properties.Delegates

/**
 *
 * @author by lzz
 * @date 2020/3/5
 * @description
 */
abstract class BaseApp(private val routerManagerImpl: IRouterManager) : Application() {
    protected var isShowLifecycle = false
    private var isDebug = BuildConfig.DEBUG

    /**
     * 用来初始化组件服务
     */
    private val mAppDelegateList: MutableList<IApplicationDelegate> = mutableListOf()


    companion object {
        var context: Application by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        registerActivityLifecycleCallbacks(CustomActivityLifecycleCallbacks(isShowLifecycle))
        initARoute()
        initIApplicationDelegate()
        SPUtils.getInstance(this)
    }

    private fun initARoute() {
        if (isDebug) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }

    private fun initIApplicationDelegate() {
        val appModules = mutableListOf<Module>()
        mAppDelegateList.addAll(routerManagerImpl.getIApplicationDelegate())
        mAppDelegateList.forEach {
            it.onCreate(this)
            appModules.addAll(it.getAppModules())
        }
        startKoin {
            androidContext(this@BaseApp)
            modules(appModules)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        mAppDelegateList.forEach {
            it.attachBaseContext(base)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegateList.forEach {
            it.onTerminate()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mAppDelegateList.forEach {
            it.onLowMemory()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mAppDelegateList.forEach {
            it.onTrimMemory(level)
        }
    }


}