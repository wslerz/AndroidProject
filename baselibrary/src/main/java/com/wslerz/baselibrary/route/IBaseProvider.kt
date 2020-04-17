package com.wslerz.baselibrary.route

import com.alibaba.android.arouter.facade.template.IProvider
import com.wslerz.baselibrary.util.IApplicationDelegate

/**
 *
 * @author by lzz
 * @date 2020/4/6
 * @description
 */
interface IBaseProvider : IProvider {
    fun getIApplicationDelegate(): List<IApplicationDelegate>
}