package com.wslerz.baselibrary.mvvm.http

import com.wslerz.baselibrary.base.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 *
 * @author by lzz
 * @date 2020/2/25
 * @description
 */
open class BaseRepository {

    suspend fun <T : Any> tryExecuteResponse(
        responseBlock: (suspend CoroutineScope.() -> BaseResponse<T>)
    ): BaseResult<T> {
        return try {
            coroutineScope {
                val response = responseBlock()
                if (response.code != HttpConstant.CODE_SUCCESS) {
                    BaseResult.Error(
                        Exception(
                            response.message,
                            Exception(response.code.toString())
                        )
                    )
                } else {
                    BaseResult.Success(response.data)
                }
            }
        } catch (e: Exception) {
            BaseResult.Error(ExceptionConverter.convertException(e))
        }
    }

    suspend fun <T : Any> tryExecute(
        responseBlock: (suspend CoroutineScope.() -> T)
    ): BaseResult<T> {
        return try {
            coroutineScope {
                val response = responseBlock()
                BaseResult.Success(response)
            }
        } catch (e: Exception) {
            BaseResult.Error(ExceptionConverter.convertException(e))
        }
    }
}
