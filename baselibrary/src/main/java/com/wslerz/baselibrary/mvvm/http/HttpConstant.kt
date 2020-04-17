package com.wslerz.baselibrary.mvvm.http

/**
 * @author by lzz
 * @date 2019/12/4
 * @description
 */
object HttpConstant {
    //http默认状态码
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val REQUEST_TIMEOUT = 408
    const val INTERNAL_SERVER_ERROR = 500
    const val BAD_GATEWAY = 502
    const val SERVICE_UNAVAILABLE = 503
    const val GATEWAY_TIMEOUT = 504

    /**
     * 默认成功返回SUCCESS
     */
    const val CODE_SUCCESS = 200
    /**
     * 未登录
     */
    const val CODE_NO_LOGIN = UNAUTHORIZED


    //自定义网络请求状态码
    const val NET_ERROR_CODE = 0x400
    const val SERVER_ERROR_CODE = 0x500
    const val DATA_ERROR_CODE = 0x600
    const val NET_ERROR_MSG = "网络请求失败，请检查您的网络设置"
    const val SERVER_ERROR_MSG = "服务器异常，请稍后再试"
    const val DEFAULT_ERROR_MSG = "数据请求失败，请稍后再试"
    const val NO_LOGIN_ERROR_MSG = "暂未登录或登录已经过期"
}