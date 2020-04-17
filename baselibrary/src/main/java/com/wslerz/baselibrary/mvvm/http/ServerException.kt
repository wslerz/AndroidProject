package com.wslerz.baselibrary.mvvm.http

/**
 * @author by lzz
 * @date 2019/12/4
 * @description
 */
class ServerException(val code: Int, val msg: String) : RuntimeException()