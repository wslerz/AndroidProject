package com.wslerz.baselibrary.route

import android.content.Context
import com.wslerz.baselibrary.util.IApplicationDelegate

/**
 *
 * @author by lzz
 * @date 2020/4/7
 * @description
 */
interface IRouterManager : IBaseProvider {
    override fun getIApplicationDelegate(): List<IApplicationDelegate>
    override fun init(context: Context?)
}
