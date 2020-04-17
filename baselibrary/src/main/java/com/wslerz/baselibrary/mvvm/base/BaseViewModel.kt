package com.wslerz.baselibrary.mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wslerz.baselibrary.mvvm.http.BaseResult
import com.wslerz.baselibrary.mvvm.http.HttpConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * @author by lzz
 * @date 2020/2/27
 * @description
 */
open class BaseViewModel : ViewModel() {
    val mExceptionLiveData = MutableLiveData<Throwable>()
    val mLoginException = MutableLiveData<Throwable>()

    /**
     * 通过协程处理请求和 处理失败成功的回调
     *  失败中的暂未登录或token已经过期自己处理
     */
    @JvmOverloads
    protected fun <T : Any> launchBlock(
        responseBlock: suspend CoroutineScope.() -> BaseResult<T>,
        successBlock: (suspend CoroutineScope.(T) -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.(Exception) -> Unit)? = null
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val result = withContext(Dispatchers.IO) {
                responseBlock()
            }
            withContext(Dispatchers.Main) {
                when (result) {
                    is BaseResult.Success<T> -> {
                        successBlock?.let {
                            it(result.data)
                        }
                    }
                    is BaseResult.Error -> {
                        errorBlock?.let {
                            val cause = result.exception.cause
                            if (cause != null && cause.message == HttpConstant.CODE_NO_LOGIN.toString()) {
                                mLoginException.value = result.exception
                            } else {
                                it(result.exception)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 无需自己处理失败的回调
     */
    @JvmOverloads
    protected fun <T : Any> launchSucBlock(
        responseBlock: suspend CoroutineScope.() -> BaseResult<T>,
        successBlock: (suspend CoroutineScope.(T) -> Unit)? = null,
        errorBlock: MutableLiveData<Throwable> = mExceptionLiveData
    ) {
        launchBlock(
            responseBlock, successBlock, {
                errorBlock.value = it
            }
        )
    }

    /**
     *无需自己处理成功和失败的回调
     */
    @JvmOverloads
    protected fun <T : Any> launchBlock(
        responseBlock: (suspend CoroutineScope.() -> BaseResult<T>),
        successBlock: MutableLiveData<T>?,
        errorBlock: MutableLiveData<Throwable>? = mExceptionLiveData
    ) {
        launchBlock(
            responseBlock, {
                successBlock?.value = it
            }, {
                errorBlock?.value = it
            }
        )
    }

}