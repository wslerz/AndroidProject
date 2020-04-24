package com.wslerz.baselibrary.mvvm.http

import com.wslerz.baselibrary.base.IBaseResponse
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
        responseBlock: (suspend CoroutineScope.() -> IBaseResponse<T>)
    ): BaseResult<T> {
        return try {
            coroutineScope {
                val response = responseBlock()
                if (!response.isSuc()) {
                    BaseResult.Error(
                        Exception(
                            response.obtainMessage(),
                            Exception(response.obtainCode())
                        )
                    )
                } else {
                    response.obtainData()?.let {
                        BaseResult.Success(it)
                    } ?: let {
                        BaseResult.Error(NullDataException())
                    }

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
