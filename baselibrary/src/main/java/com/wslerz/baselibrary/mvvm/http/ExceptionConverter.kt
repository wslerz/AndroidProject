package com.wslerz.baselibrary.mvvm.http

import android.util.Log
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * @author by lzz
 * @date 2019/12/4
 * @description 网络请求异常处理
 */
object ExceptionConverter {
    private const val TAG = "ExceptionConverter"
    fun convertException(e: Exception): Exception {
        Log.e(TAG, e.toString())
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    HttpConstant.REQUEST_TIMEOUT, HttpConstant.GATEWAY_TIMEOUT, HttpConstant.BAD_GATEWAY ->
                        Exception(
                            HttpConstant.NET_ERROR_MSG,
                            Throwable(HttpConstant.NET_ERROR_CODE.toString())
                        )
                    HttpConstant.SERVICE_UNAVAILABLE, HttpConstant.FORBIDDEN, HttpConstant.NOT_FOUND, HttpConstant.INTERNAL_SERVER_ERROR ->
                        Exception(
                            HttpConstant.SERVER_ERROR_MSG,
                            Throwable(HttpConstant.SERVER_ERROR_CODE.toString())
                        )
                    HttpConstant.UNAUTHORIZED ->
                        Exception(
                            HttpConstant.NO_LOGIN_ERROR_MSG,
                            Throwable(HttpConstant.CODE_NO_LOGIN.toString())
                        )

                    else ->
                        Exception(
                            HttpConstant.SERVER_ERROR_MSG,
                            Throwable(HttpConstant.SERVER_ERROR_CODE.toString())
                        )
                }
            }
            is ServerException ->
                Exception(
                    e.message,
                    Throwable(e.code.toString() + "")
                )
            is JsonParseException, is JSONException, is ParseException ->
                Exception(
                    HttpConstant.SERVER_ERROR_MSG,
                    Throwable(HttpConstant.SERVER_ERROR_CODE.toString())
                )
            is ConnectException, is SocketTimeoutException ->
                Exception(
                    HttpConstant.NET_ERROR_MSG,
                    Throwable(HttpConstant.NET_ERROR_CODE.toString())
                )
            is UnknownHostException ->
                Exception(
                    HttpConstant.SERVER_ERROR_MSG,
                    Throwable(HttpConstant.SERVER_ERROR_CODE.toString())
                )
            else ->
                Exception(
                    HttpConstant.DEFAULT_ERROR_MSG,
                    Throwable(HttpConstant.DATA_ERROR_CODE.toString())
                )
        }
    }
}