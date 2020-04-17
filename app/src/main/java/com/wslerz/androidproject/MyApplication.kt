package com.wslerz.androidproject

import com.wslerz.androidproject.route.RouterManager
import com.wslerz.baselibrary.base.BaseApp

/**
 *
 * @author by lzz
 * @date 2020/4/16
 * @description
 */
class MyApplication:BaseApp(RouterManager) {
    init {
        isShowLifecycle = true
    }
}