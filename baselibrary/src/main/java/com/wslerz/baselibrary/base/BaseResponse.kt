package com.wslerz.baselibrary.base

/**
 * @author by lzz
 * @date 2019/12/3
 * @description
 */
data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)