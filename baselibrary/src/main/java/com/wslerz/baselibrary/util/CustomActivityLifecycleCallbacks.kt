package com.wslerz.baselibrary.util

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 *
 * @author by lzz
 * @date 2020/4/16
 * @description
 */
class CustomActivityLifecycleCallbacks(private val isShowLifecycle: Boolean) :
    Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {
        showActivityLifecycle("onActivityPaused", activity)
    }

    override fun onActivityResumed(activity: Activity?) {
        showActivityLifecycle("onActivityResumed", activity)
    }

    override fun onActivityStarted(activity: Activity?) {
        showActivityLifecycle("onActivityStarted", activity)
    }

    override fun onActivityDestroyed(activity: Activity?) {
        showActivityLifecycle("onActivityDestroyed", activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        showActivityLifecycle("onActivitySaveInstanceState", activity)
    }

    override fun onActivityStopped(activity: Activity?) {
        showActivityLifecycle("onActivityStopped", activity)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        showActivityLifecycle("onActivityCreated", activity)
    }


    private fun showActivityLifecycle(lifecycle: String, activity: Activity?) {
        if (isShowLifecycle) {
            LogUtils.i("${activity?.componentName?.className}---$lifecycle")
        }
    }
}