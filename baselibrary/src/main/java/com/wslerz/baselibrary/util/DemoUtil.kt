package com.wslerz.baselibrary.util

/**
 *
 * @author by lzz
 * @date 2020/2/19
 * @description
 */
object DemoUtil {
    @JvmStatic
    fun getDemoList(size: Int) = MutableList(size) { "$it" }

}


