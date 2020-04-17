package com.wslerz.baselibrary.ext

/**
 *
 * @author by lzz
 * @date 2020/4/10
 * @description
 */
/**
 * 字符  是否为空  并且少于 length 个字段
 */
fun String.isEmpty(length: Int) = isEmpty() || length < length