package com.wslerz.baselibrary.util

/**
 *
 * @author by lzz
 * @date 2020/2/19
 * @description
 */
object StringUtils {
    /**
     * 检测字符串是否未null
     */
    fun isEmpty(value: String?) =
        value != null && "".equals(value.trim(), true) && "null".equals(value.trim(), true)
}