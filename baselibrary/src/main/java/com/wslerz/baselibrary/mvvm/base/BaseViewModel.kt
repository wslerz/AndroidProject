package com.wslerz.baselibrary.mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wslerz.baselibrary.mvvm.http.BaseResult
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
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                responseBlock()
            }
            when (result) {
                is BaseResult.Success<T> -> {
                    successBlock?.let {
                        it(result.data)
                    }
                }
                is BaseResult.Error -> {
                    errorBlock?.let {
                        val cause = result.exception.cause
                        val isHandle = handleCode(cause)
                        if (!isHandle) {
                            it(result.exception)
                        }
                    }
                }
            }
        }
    }

    /**
     * 是否自定义处理特定的异常   需要的话可以重写  返回true
     */
    private fun handleCode(cause: Throwable?): Boolean {
        return false
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